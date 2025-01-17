import JSZip from "jszip";
import { type InputType, type OutputType } from "jszip";
import * as XLSX from 'xlsx';

/**
 * @param file
 * @returns {file을 ArrayBuffer로 변경}
 */
export const fileToArrayBuffer = (file: File): Promise<unknown> => {
  return new Promise((resolve, _reject) => {
    const reader: FileReader = new FileReader();
    reader.onload = () => {
      resolve(reader.result);
    };
    reader.readAsArrayBuffer(file);
  });
}

/**
 * @param blob
 * @returns {blob ArrayBuffer로 변경}
 */
export const blobToArrayBuffer = (blob: Blob): Promise<unknown> => {
  return new Promise((resolve, _reject) => {
    const reader: FileReader = new FileReader();

    reader.onload = () => {
      resolve(reader.result);
    };

    reader.readAsArrayBuffer(blob);
  });
}

/**
 * Blob형태의 파일 다운로드
 * @param file
 * @param fileName
 */
export const blobFileDownload = (file: Blob, fileName: string) => {
  const blob = new Blob([file]);
  const url = URL.createObjectURL(blob);
  const link = document.createElement('a');

  link.href = url;
  link.download = fileName;
  link.click();
  URL.revokeObjectURL(url);

}

/**
 *
 * @param size
 * @returns {byte 단위의 size KB , MB, GB, TB 반환}
 */
export const getByteSize = (size:number,fixed:number=1) => {
  const byteUnits = ["KB", "MB", "GB", "TB"];
  for (let i = 0; i < byteUnits.length; i++) {
    size = size / 1024;
    if (size < 1024) return size.toFixed(fixed) + byteUnits[i];
  }
};

/**
 *
 * @param size
 * @param multiNum
 * @param byte
 * @returns {byte 단위의 size 용량 체크 ture false 반환}
 */
export const checkfileSize = (size:number, multiNum:number, byte:string='Byte') => {
  const unit = ["Byte", "KB", "MB", "GB", "TB"].indexOf(byte);
  const maxSize = Math.pow(1024, unit) * multiNum;
  return size < maxSize;
};


/**
 * blob 데이터 file 으로 변경
 * @param blob
 * @param fileName
 * @returns
 */
export const blobToFile = async (blob: Blob, fileName: string, type?: string): Promise<File> => {
  const blobParts: BlobPart[] = [blob];
  const fileOptions: FilePropertyBag = { type: type || blob.type };

  try {
    const file = new File(blobParts, fileName, fileOptions);
    return file;
  } catch (error) {
    throw error;
  }
}


/**
 * 이미지 파일 width, height 가져오기
 * @param file
 * @returns { 이미지 width: number; height: number; }
 */
export const getImageDimensions = (file: File): Promise<{ width: number; height: number }> => {
  return new Promise<{ width: number; height: number; }>((resolve, reject) => {
    if (!file.type.startsWith('image/')) {
      return;
    }
    const reader = new FileReader();
    reader.onload = (e) => {
      const image = new Image();
      image.src = e.target?.result as string;

      image.onload = () => {
        const dimensions = { width: image.width, height: image.height };
        resolve(dimensions);
      };
      image.onerror = () => {
        reject();
      };

      reader.onerror = () => {
        reject();
      };
    };

    reader.readAsDataURL(file);
  });
}

/**
 * string 파일만들고 압축
 * @param str 압축할 string
 * @param filename 파일명
 * @returns  { retun Promise<Blob> }
 */
export const fileZip = async (str: string, filename: string): Promise<Blob> => {
  const textData: Uint8Array = new TextEncoder().encode(str);
  const zip = new JSZip();
  zip.file(`${filename}`, textData);
  return await zip.generateAsync({ type: "blob" });
}


/**
 * 압축풀기
 * @param file Blob file
 * @returns { return Promise<any[]> }
 */
export const loadZipFile = async (file: Blob, outputType: OutputType): Promise<any[]> => {
  const zip = new JSZip();
  const zipContents = await zip.loadAsync(file);
  const contents = [];

  for (const [filename, data] of Object.entries(zipContents.files)) {
    if (!data.dir) {
      const unzipData = await data.async(outputType);
      contents.push(unzipData);
    }
  }

  return contents
}


/**
 * 엑셀 파일 to JSON
 * @param file
 */
export const readExcelFile = async (file: File): Promise<unknown> => {
  const reader = new FileReader();
  reader.readAsArrayBuffer(file);

  return new Promise((resolve, _reject) => {
    reader.onload = (e) => {
      const data = e.target!.result;
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetName = workbook.SheetNames[0];
      const worksheet = workbook.Sheets[sheetName];
      const jsonData = XLSX.utils.sheet_to_json(worksheet, { defval: null });
      resolve(jsonData)
    };
  });
}


/**
 * 엑셀 파일 특정 sheet, 특정 row
 * @param file
 */
export const readExceltRow = async (
  file: File,
  sheetNum: number = 0,
  rowNum: number = 0
): Promise<unknown> => {
  const reader = new FileReader();
  reader.readAsArrayBuffer(file);

  return new Promise((resolve, _reject) => {
    reader.onload = (e) => {
      const data = e.target!.result;
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetName = workbook.SheetNames[sheetNum];
      const worksheet = workbook.Sheets[sheetName];
      const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 })[rowNum];
      resolve(jsonData)
    };
  });
}


/**
 * 암호화 파일 여부
 * // this Document is encrypted and protected by Fasoo DRM
 * @param file
 * @returns { Promise<boolean> }
 */
export const isEncryptedFile = (file: File | Blob): Promise<boolean> => {
  return new Promise((resolve, _reject) => {
    const reader: FileReader = new FileReader();
    reader.onload = (e) => {
      const content = e.target?.result as string;
      const rows = content.split('\n');
      const firstRow = rows[0];
      resolve(firstRow.indexOf("encrypted") > -1);
    };
    reader.readAsText(file);
  });
}

