// 전역 타입 선언 파일

export {}

declare global {
  //파일 업로드,업데이트시 필요한 객체 값
  interface FileObj {
    attachId?: string
    file?: File
    fileName: string
    fileSize?: string
    title?: string
  }
}
