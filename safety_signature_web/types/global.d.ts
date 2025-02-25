// 전역 타입 선언 파일

export {}

declare global {
  interface Pageable {
    page?: number
    size?: number
  }
  //파일 업로드,업데이트시 필요한 객체 값
  interface FileObj {
    attachId?: string
    file?: File
    fileName: string
    fileSize?: string | number
    title?: string
  }
  /**
   * 네이버 맵 사용을 위한 타입 설정
   */
  type NaverMap = naver.maps.Map
  type Lat = number
  type Lng = number
  type Coordinates = [Lat, Lng]
  /**
   * 네이버 맵 사용을 위한 타입 설정
   */
  //회원정보 테이블
  interface UserMasterType {
    email: string
    googleSignIn: boolean
    id: string
    kakaoSignIn: boolean
    mobile: string
    name: string
    naverSignIn: boolean
    profileImageUri?: string
    signatureDocId?: string
    userPassword?: string
    userStatusCode: string
    userTypeCode: string
  }
  interface BulletinBoardMasterType {
    attachYn: boolean
    boardContents: string
    boardTitle: string
    completionYn?: boolean
    createdBy: string
    createdDate: string
    createdDateFormat: string
    id: string
    lastModifiedBy: string
    lastModifiedDate: string
    userMasterDTO: UserMasterType
    userMasterId: string
    signatureCount: number
    attachDocList: AttachDocMasterType[]
  }

  interface AttachDocMasterType {
    id: string
    attachDocOwnerId: string
    attachDocName: string
    attachFileType: any
    attachDocExplain: string
    attachDocId: string
    attachDocPosition: string
    attachDocOwnerClassCode: string
    classValue: any
    attachDocReader: any
    attachDocReaderXlsx: any
    attachDocPositionFilename: any
    attachDocSize: number
    attachDocPartitionDTO: any
    attachDownloadLimitDtime: any
    attachStorageLimitDtime: any
    minioDeleteYn: any
  }
}
