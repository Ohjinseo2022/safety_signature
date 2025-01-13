package com.safety_signature.safety_signature_back.utils;

import com.google.common.io.ByteSource;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.DownloadResourceDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.MinIOUtilsReturnDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.PartitionDTO;
import com.safety_signature.safety_signature_back.app.common.enumeration.ContentsCatalogClassValue;
import com.safety_signature.safety_signature_back.config.payload.FileResponse;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class MinioUtils {

    @Autowired
    MinioClient minioClient;

    @Value("${client.minio.bucket}")
    private String bucket;

    @Value("${client.minio.bucketImage}")
    private String bucketImage;

    @Value("${client.minio.imageCompanyNo:mp_attach_comp_img/LICENSE/no-img.png}")
    private String imageCompanyNo;

    /**
     *  버킷 하위에 디렉토리 생성 규칙
     * 스마트리온 일반 문서 : 7개의 카테고리를 디렉토리로 생성하고, 그 하위에 YYYYMM 형식의 디렉토리를 만든다
     * mp_attach_lotte_doc : 롯데그룹사 문서 파일을 담을 경로
     * mp_attach_comp_doc : 일반기업(공급사)의 문서 파일을 담을 경로
     * mp_attach_inquiry : 문의내역의 첨부 파일을 담을 경로
     * mp_attach_notice : 공지사항의 첨부 파일을 담을 경로
     * mp_attach_contents_doc(상품의 문서 파일을 담을 경로)
     * mp_attach_contents_data(상품의 DB 파일[CSV, XML, JSON]을 담을 경로)
     * mp_attach_trend : 트렌드모니터 정보의 첨부 파일을 담을 경로
     * 스마트리온 이미지 첨부 : 3개의 카테고리를 디렉토리로 생성한다.
     * mp_attach_lotte_img : 롯데그룹사 이미지 파일을 담을 경로
     * mp_attach_comp_img : 일반기업(공급사)의 이미지 파일을 담을 경로
     * mp_attach_contents_img(상품의 상세 이미지 파일을 담을 경로)
     *
     * 디렉토리 예제) mp_attach_lotte_doc/202306/
     *
     * 디렉토리 끝부분에 / 포함
     * 아래 경로에서 오브젝트 확인가능
     */
    public String upload(MultipartFile file, String directory) throws Exception {
        return upload(file, directory, false);
    }
    public String upload(MultipartFile file, String directory, boolean isImage) throws Exception {

        // 초기화 (디렉토리) : 없으면 생성
//===============================================================
//        String path = String.format("%s/%s/%s/%s", this.ftpPath, company, IoUtils.now("yyyyMMdd"), userid);
//        checkAndMakeDirectory(path);

//===============================================================
// 파일 정보 생성
//===============================================================
        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileNameFirst = fileName.replaceAll("."+extension, "");

        LocalDateTime date = LocalDateTime.now();
        String formatedFileNow = date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));

        String uploadFileFullName = String.format("%s_%s.%s", fileNameFirst, formatedFileNow, extension);

        String obj = directory + "/" + uploadFileFullName;

        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(isImage ? bucketImage : bucket) // 버킷명 수정하고 폴더 설정하는 테스트해볼것
                .object(obj)
                .stream(file.getInputStream(), file.getInputStream().available(), -1)
                .build());

        return uploadFileFullName;
        // 동일한 경로/이름 파일 업로드시 기존오브젝트를 삭제해야 하는가?
    }
    public MinIOUtilsReturnDTO base64StringImageUpload(String base64StringSignatureImage , String uuId) throws Exception{
        byte[] decodedBytes = Base64.getDecoder().decode(base64StringSignatureImage);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);
        String directory = String.format("signature/%s/", uuId);
        LocalDateTime date = LocalDateTime.now();
        String formatedFileNow = date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
        String uploadFileFullName = String.format("%s_%s.png", uuId, formatedFileNow);
        String attachDocPosition = directory + "/" + uploadFileFullName;

        // 파일 이름을 저장소에 기록하거나 별도로 사용
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(attachDocPosition) // 파일 이름 전달
                .stream(inputStream, decodedBytes.length, -1)
                .contentType("image/png") // 적절한 Content-Type 설정
                .build());
        return MinIOUtilsReturnDTO.builder().attachDocPosition(attachDocPosition).attachDocName(uploadFileFullName).attachDocSize(FileUtil.getFileSize(decodedBytes.length)).build();
    }
    
    public String uploadEp(MultipartFile file, String bucket) throws Exception {

        // 초기화 (디렉토리) : 없으면 생성
		//===============================================================
		//        String path = String.format("%s/%s/%s/%s", this.ftpPath, company, IoUtils.now("yyyyMMdd"), userid);
		//        checkAndMakeDirectory(path);
		
		//===============================================================
		// 파일 정보 생성
		//===============================================================
        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileNameFirst = fileName.replaceAll("."+extension, "");

        LocalDateTime date = LocalDateTime.now();
        String formatedFileNow = date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));

        String uploadFileFullName = String.format("%s_%s.%s", fileNameFirst, formatedFileNow, extension);

        String obj = uploadFileFullName;

        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket.toLowerCase()+"-share-bucket") 
                .object(obj)
                .stream(file.getInputStream(), file.getInputStream().available(), -1)
                .build());

        return uploadFileFullName;
    }

    public String upload(String directory, String fileName, InputStream i) throws Exception {

        String obj = directory + "/" + fileName;

        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket) // 버킷명 수정하고 폴더 설정하는 테스트해볼것
                .object(directory+ "/" + fileName)
                .stream(i, i.available(), -1)
                .build());

        return obj;

    }

    /**
     *  버킷 하위에 디렉토리 생성 규칙
     * 스마트리온 일반 문서 : 7개의 카테고리를 디렉토리로 생성하고, 그 하위에 YYYYMM 형식의 디렉토리를 만든다
     * mp_attach_lotte_doc : 롯데그룹사 문서 파일을 담을 경로
     * mp_attach_comp_doc : 일반기업(공급사)의 문서 파일을 담을 경로
     * mp_attach_inquiry : 문의내역의 첨부 파일을 담을 경로
     * mp_attach_notice : 공지사항의 첨부 파일을 담을 경로
     * mp_attach_contents_doc(상품의 문서 파일을 담을 경로)
     * mp_attach_contents_data(상품의 DB 파일[CSV, XML, JSON]을 담을 경로)
     * mp_attach_trend : 트렌드모니터 정보의 첨부 파일을 담을 경로
     * 스마트리온 이미지 첨부 : 3개의 카테고리를 디렉토리로 생성한다.
     * mp_attach_lotte_img : 롯데그룹사 이미지 파일을 담을 경로
     * mp_attach_comp_img : 일반기업(공급사)의 이미지 파일을 담을 경로
     * mp_attach_contents_img(상품의 상세 이미지 파일을 담을 경로)
     *
     * 디렉토리 예제) mp_attach_lotte_doc/202306/
     */
    public Resource download(String fileName, String directory) throws Exception {
        String object = directory + "/" + fileName;
        InputStream obj = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(object).build());

        // Heap 메모리를 사용하지 않도록 스트림객체를 그대로 사용하도록 수정
        return new InputStreamResource(obj);

//        byte[] content = IOUtils.toByteArray(obj);
//        obj.close();
//
//        ByteArrayResource resource = new ByteArrayResource(content);
//
//        return resource;
    }

    public Resource download(String fileName) throws Exception {
        return download(fileName, false);

    }

    public Resource download(String fileName, Boolean isImage) throws Exception {
        InputStream obj = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(isImage ? bucketImage : bucket) // 버킷명 수정하고 폴더 설정하는 테스트해볼것
                        .object(fileName).build());

        // Heap 메모리를 사용하지 않도록 스트림객체를 그대로 사용하도록 수정
        return new InputStreamResource(obj);

        // Heap 메모리 문제로 인하여 코드 삭제
//        byte[] content = IOUtils.toByteArray(obj);
//        obj.close();
//
//        ByteArrayResource resource = new ByteArrayResource(content);
//
//        return resource;
    }

    public String getBufferedReader (String fileName) {

        String rtn = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;

        final String[] EXTENSION = { "PDF", "XLSX"};

        try {
            stringBuilder = new StringBuilder();

            if(Arrays.stream(EXTENSION).map(e -> fileName.toUpperCase().contains(e)).count() == 0) {
                throw new RuntimeException("허용되는 확장자가 아닙니다. : " + fileName);
            }

            bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    this.download(fileName).getInputStream()
                            )
                    );

            for (int data = bufferedReader.read(); data != -1; data = bufferedReader.read()) {
                stringBuilder.append((char)data);
            }

            rtn = stringBuilder.toString();

        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            }
            catch (IOException e) {}

            stringBuilder.setLength(0);
        }

        return rtn;
    }

    public List<List<String>> getBufferedReaderXlsx (String fileName) {

        List<List<String>> list = new ArrayList<>();
        final String[] EXTENSION = { "PDF", "XLSX"};

        try {
            if(Arrays.stream(EXTENSION).map(e -> fileName.toUpperCase().contains(e)).count() == 0) {
                throw new RuntimeException("허용되는 확장자가 아닙니다. : " + fileName);
            }

            XSSFWorkbook workbook = new XSSFWorkbook(this.download(fileName).getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum() > 100 ? 100 : sheet.getLastRowNum();

            for (int i = 0; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    List<String> cellList = new ArrayList<String>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            cellList.add(cellReader(cell)); //셀을 읽어와서 List에 추가
                        }
                    }
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(cellList.get(0))) {
                        list.add(cellList); // 추가된 로우List를 List에 추가
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return list;

    }

    public List<List<String>> getBufferedReaderXlsx(MultipartFile file, int sheetIndex) throws IOException {

        List<List<String>> list = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(file.getResource().getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    List<String> cellList = new ArrayList<String>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            cellList.add(cellReader(cell)); //셀을 읽어와서 List에 추가
                        }
                    }
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(cellList.get(0))) {
                        list.add(cellList); // 추가된 로우List를 List에 추가
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return list;
    }


    private static String cellReader(XSSFCell cell) {
        String value = "";
        CellType ct = cell.getCellTypeEnum();
        if(ct != null) {
            switch(cell.getCellTypeEnum()) {
                case FORMULA:
                    value = cell.getCellFormula();
                    break;
                case NUMERIC:
                    value=cell.getNumericCellValue()+"";
                    break;
                case STRING:
                    value=cell.getStringCellValue()+"";
                    break;
                case BOOLEAN:
                    value=cell.getBooleanCellValue()+"";
                    break;
                case ERROR:
                    value=cell.getErrorCellValue()+"";
                    break;
            }
        }

        return value;
    }

    /**
     * To-DO getFile 몇가지 설정더 해야함. 2023-07-12
     * @return
     * @throws Exception
     */
    public FileResponse getFile(AttachDocMasterDTO attachDocMasterDTO) throws Exception {

        InputStream metadata = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(attachDocMasterDTO.getAttachDocPosition()+attachDocMasterDTO.getAttachDocName()).build());

        InputStreamResource inputStreamResource = new InputStreamResource(metadata);

        MediaType thisMediaType = MediaType.APPLICATION_OCTET_STREAM;

        if(attachDocMasterDTO.getAttachDocName().toUpperCase().indexOf("PDF") > -1){
            thisMediaType = MediaType.APPLICATION_PDF;
        }

        return FileResponse.builder()
                .filename(UriEncoder.encode(attachDocMasterDTO.getAttachDocName()))
                .fileSize(attachDocMasterDTO.getAttachDocSize())
                .contentType(thisMediaType)
                .createdTime(DateUtil.nowForLocalDateTime())
                .stream(inputStreamResource)
                .build();

    }

    public FileResponse getFileTypeSecond(AttachDocMasterDTO attachDocMasterDTO) throws Exception {

        InputStream metadata = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(attachDocMasterDTO.getAttachDocPosition()).build());

        InputStreamResource inputStreamResource = new InputStreamResource(metadata);

        MediaType thisMediaType = MediaType.APPLICATION_OCTET_STREAM;

        if(attachDocMasterDTO.getAttachDocName().toUpperCase().indexOf("PDF") > -1){
            thisMediaType = MediaType.APPLICATION_PDF;
        }

        return FileResponse.builder()
                .filename(UriEncoder.encode(attachDocMasterDTO.getAttachDocName()))
                .fileSize(attachDocMasterDTO.getAttachDocSize())
                .contentType(thisMediaType)
                .createdTime(DateUtil.nowForLocalDateTime())
                .stream(inputStreamResource)
                .build();

    }


    public Resource downloadCompanyNoImage() throws Exception {
        return download(imageCompanyNo, true);
    }


    public static String makeFileName(MultipartFile multipartFile) {

        return
                String.format("%s_%s.%s",
                        multipartFile.getOriginalFilename().replaceAll(
                                "."+ StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()), ""),
                        DateUtil.nowDatetime("yyyyMMdd'T'HHmmss"),
                        StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()))
                ;

    }

    public ContentsCatalogClassValue setContentsCatalogClassValue(String fileName) {

        ContentsCatalogClassValue classValue = ContentsCatalogClassValue.CSV;

        if (fileName.toUpperCase().indexOf(".CSV") > -1) {
            classValue = ContentsCatalogClassValue.CSV;
        } else if (fileName.toUpperCase().indexOf(".PDF") > -1) {
            classValue = ContentsCatalogClassValue.PDF;
        } else if (fileName.toUpperCase().indexOf(".XML") > -1) {
            classValue = ContentsCatalogClassValue.XML;
        } else if (fileName.toUpperCase().indexOf(".JSON") > -1) {
            classValue = ContentsCatalogClassValue.JSON;
        } else if (fileName.toUpperCase().indexOf(".XLSX") > -1) {
            classValue = ContentsCatalogClassValue.XLSX;
        }

        return classValue;

    }

    public void removeObject(String object,Boolean isImage) throws Exception {
//        public void removeObject(String object,Boolean isImage) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(isImage ? bucketImage : bucket)
                .object(object)
                .build());

    }


    /**
     *
     * @param file
     * @param directory
     * @param fileName
     * @param isImage
     * @return
     * @throws Exception
     * 설명 : pdf 파일 페이지별 이미지 변환후 MinIO 저장
     */
    public List<PartitionDTO> convertPdfToImageUploadReturnUrlPathList(MultipartFile file, String directory, String fileName, Boolean isImage) throws Exception {

        PDDocument doc = Loader.loadPDF(file.getBytes());
        PDFRenderer renderer = new PDFRenderer(doc);
        List<PartitionDTO> imageList = new ArrayList<>();

        for (int i = 0; i < doc.getNumberOfPages(); i++) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage image = renderer.renderImageWithDPI(i, 200);
            ImageIO.write(image, "JPEG", baos); // JPEG or PNG
            InputStream targetStream = ByteSource.wrap(baos.toByteArray()).openStream();
            String objectFullPath = directory+fileName
                    .replace(".pdf","-"+(i+1)+".jpg")
                    .replace(".PDF","-"+(i+1)+".jpg");

            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(isImage ? bucketImage : bucket)
                    .object(objectFullPath)
                    .stream(targetStream, targetStream.available(), -1)
                    .build());

            imageList.add(
                    PartitionDTO.builder()
                        .pageTurn(Long.valueOf(i+1))
                        .attachDocPosition(objectFullPath)
                        .attachDocSize(
                                BigDecimal.valueOf(targetStream.available()).
                                        divide(new BigDecimal("1024"), 5, BigDecimal.ROUND_UP))
                        .build()
            );

            targetStream.close();
            baos.close();

        }

        doc.close();

        return imageList;

    }

    public List<PartitionDTO> convertPdfToImageUploadReturnUrlPathList(MultipartFile file, String directory, String fileName) throws Exception {

        PDDocument doc = Loader.loadPDF(file.getBytes());
        PDFRenderer renderer = new PDFRenderer(doc);
        List<PartitionDTO> imageList = new ArrayList<>();

        for (int i = 0; i < doc.getNumberOfPages(); i++) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage image = renderer.renderImageWithDPI(i, 200);
            ImageIO.write(image, "JPEG", baos); // JPEG or PNG
            InputStream targetStream = ByteSource.wrap(baos.toByteArray()).openStream();
            String objectFullPath = directory+fileName+"-"+i+".jpg";
            int size = targetStream.available();

            ObjectWriteResponse dd = minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(bucket)
                    .object(objectFullPath)
                    .stream(targetStream, size, -1)
                    .build());

            imageList.add(
                    PartitionDTO.builder()
                            .pageTurn(Long.valueOf(i))
                            .attachDocPosition(objectFullPath)
                            .attachDocSize(BigDecimal.valueOf(size))
                            .build()
            );

            targetStream.close();
            baos.close();

        }

        doc.close();

        return imageList;

    }

    public InputStream getFile(String filePath) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(filePath).build());
    }

    public byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] buffer = new byte[2048];
        try {
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void compressedDownloadProcessing(OutputStream outputStream, List<DownloadResourceDTO> downloadResourceDTOList) throws Exception {
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            int i = 1;
            for (DownloadResourceDTO downloadResourceDTO: downloadResourceDTOList) {
                ZipEntry zipEntry = new ZipEntry(String.format("%s. %s", i, downloadResourceDTO.getFileName()));
                zipOutputStream.putNextEntry(zipEntry);
                InputStream inputStream = downloadResourceDTO.getResource().getInputStream();
                zipOutputStream.write(inputStreamToByteArray(inputStream));
                StreamUtils.copy(inputStream, zipOutputStream);
                zipOutputStream.closeEntry();
                i++;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
