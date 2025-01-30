export enum LoginResponseCode {
  LoginResponseToken = 'LoginResponseToken',
  LoginResponseFailed = 'LoginResponseFailed',
  LoginResponseSuccess = 'LoginResponseSuccess',
}
export enum TokenCode {
  accessToken = 'accessToken',
  refreshToken = 'refreshToken',
}
export enum UserTypeCode {
  MASTER_ADMINISTRATOR = 'MASTER_ADMINISTRATOR',
  CORPORATE_MANAGER = 'CORPORATE_MANAGER',
  GENERAL_MEMBER = 'GENERAL_MEMBER',
}
// MASTER_ADMINISTRATOR('마스터 관리자 계정'),
//   CORPORATE_MANAGER('기업관리자'),
//   GENERAL_MEMBER('일반 회원')
export interface LoginResponseBase {
  type: LoginResponseCode
}
export interface LoginRequestType {
  userId: string
  password: string
}
export interface LoginResponseToken extends LoginResponseBase {
  accessToken: string
  refreshToken: string
}
export interface LoginResponseFailed extends LoginResponseBase {
  msg: string
}
export interface LoginResponseSuccess extends LoginResponseBase {
  id: string
  name: string
  profileImageUri?: string
  email: string
  mobile?: string
  signatureDocId?: string
  userStatusCode: string
  userTypeCode: string
}

export const isLoginResponceToken = (obj: LoginResponseBase) => {
  return obj.type === LoginResponseCode.LoginResponseToken
}
export const isLoginResponceSuccess = (obj: LoginResponseBase) => {
  return obj.type === LoginResponseCode.LoginResponseSuccess
}
