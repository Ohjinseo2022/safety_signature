/**
 * 사업자번호 유효성 체크
 * @param businessNumber
 * @returns boolean
 */
export const validateBusinessNumber = (businessNumber: string): boolean => {
  if (businessNumber.length !== 10) {
    return false;
  }
  return true;

  // const keyArr = [1,3,7,1,3,7,1,3,5]
  // const checkDigit = Number(businessNumber[9])
  // let sum = 0

  // for (let i = 0; i < 8; i++) {
  //   sum += Number(businessNumber[i]) * keyArr[i]
  // }

  // sum += Math.floor((keyArr[8] * Number(businessNumber[8])) / 10)
  // const calculatedCheckDigit = (10 - (sum % 10)) % 10

  // return checkDigit === calculatedCheckDigit;
};

/**
 * 법인번호 유효성 체크
 * @param corporationNumber
 * @returns boolean
 */
export const validateCorporationNumber = (
  corporationNumber: string
): boolean => {
  if (corporationNumber !== "" && corporationNumber.length !== 13) {
    return false;
  }
  return true;
};

/**
 * 파일 확장자 체크
 * @param filename
 * @param isLogo
 * @returns boolean
 */
export const isAllowedExtension = (
  filename: string,
  isLogo: boolean,
  isXlsx: boolean = false
): boolean => {
  if (filename !== "") {
    let allowedExtensions: string[] = [];
    if (isLogo) {
      allowedExtensions = ["gif", "jpg", "png"];
    } else {
      if (!isXlsx) {
        allowedExtensions = ["gif", "jpg", "png", "pdf"];
      } else {
        allowedExtensions = ["gif", "jpg", "png", "pdf", "xlsx"];
      }
    }
    const parts: string[] = filename.split(".");
    if (parts.length > 1) {
      const extension: string = parts.pop()!.toLowerCase();
      return allowedExtensions.includes(extension);
    }
  }

  return false;
};

/**
 * 파일 확장자 체크
 * @param mimeType
 * @param isLogo
 * @returns boolean
 */
export const isAllowedMimeType = (
  mimeType: string,
  isLogo: boolean,
  isXlsx: boolean = false
): boolean => {
  if (mimeType !== "") {
    let allowedMimeType: string[] = [];
    if (isLogo) {
      allowedMimeType = ["image/gif", "image/jpg", "image/png"];
    } else {
      if (!isXlsx) {
        allowedMimeType = [
          "image/gif",
          "image/jpg",
          "image/png",
          "application/pdf",
        ];
      } else {
        allowedMimeType = [
          "image/gif",
          "image/jpg",
          "image/png",
          "application/pdf",
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        ];
      }
    }

    console.log("mimeType : " + mimeType);
    console.log(allowedMimeType);
    return allowedMimeType.includes(mimeType);
  }
  return false;
};

// export const isAllowedFileSize = (
//   fileName: string, fileSize: number
// ): boolean => {
//   if (fileName !== "") {
//     const allowedExtensions: string[] = ['gif', 'jpg', 'png'];
//     const parts: string[] = fileName.split('.');
//     if (parts.length > 1) {
//       const extension: string = parts.pop()!.toLowerCase();
//       if (extension === "pdf") {
//         if (checkfileSize(fileSize, 10, 'MB')) {
//           return true
//         }
//       } else if (allowedExtensions.includes(extension)) {
//         if (checkfileSize(fileSize, 5, 'MB')) {
//           return true
//         }
//       } else if(extension === 'xlsx'){
//         if (checkfileSize(fileSize, 1, 'MB')) {
//           return true
//         }
//       }
//     }
//   }

//   return false;
// };

/**
 * 파일 확장자 체크
 * @param filename
 * @param allowedFE
 * @returns boolean
 */
export const checkFileNameExtension = (
  fileName: string,
  allowedFE: string[]
): boolean => {
  if (fileName === "") {
    return false;
  }

  if (allowedFE.length <= 0) {
    return false;
  }

  const parts: string[] = fileName.split(".");

  if (parts.length > 1) {
    const extension: string = parts.pop()!.toLowerCase();
    return allowedFE.includes(extension);
  }

  return false;
};

// /**
//  * 첨부파일 SIZE 체크
//  * @param fileName
//  * @param fileSize
//  * @returns boolean
//  */
// export const checkAttachFileSize = (
//   fileName: string,
//   fileSize: number
// ): boolean => {
//   if (fileName === "") {
//     return false;
//   }

//   const parts: string[] = fileName.split(".");
//   if (parts.length > 1) {
//     const fileExtensions: string[] = ["gif", "jpg", "png"];
//     const extension: string = parts.pop()!.toLowerCase();
//     if (extension === "pdf") {
//       if (checkfileSize(fileSize, 10, "MB")) {
//         return true;
//       }
//     } else if (extension === "xlsx") {
//       if (checkfileSize(fileSize, 1, "MB")) {
//         return true;
//       }
//     } else if (fileExtensions.includes(extension)) {
//       if (checkfileSize(fileSize, 5, "MB")) {
//         return true;
//       }
//     }
//   }

//   return false;
// };
