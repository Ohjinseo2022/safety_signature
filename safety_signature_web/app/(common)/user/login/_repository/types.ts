export enum LoginResponseCode {
  LoginResponseToken = 'LoginResponseToken',
  LoginResponseFailed = 'LoginResponseFailed',
  LoginResponseSuccess = 'LoginResponseSuccess',
}
export enum TokenCode {
  accessToken = 'accessToken',
  refreshToken = 'refreshToken',
}
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
}

export const isLoginResponceToken = (obj: LoginResponseBase) => {
  return obj.type === LoginResponseCode.LoginResponseToken
}
export const isLoginResponceSuccess = (obj: LoginResponseBase) => {
  return obj.type === LoginResponseCode.LoginResponseSuccess
}
