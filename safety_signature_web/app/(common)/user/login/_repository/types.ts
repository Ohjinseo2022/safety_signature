export enum LoginResponseCode {
  LoginResponseToken = 'LoginResponseToken',
  LoginResponseFailed = 'LoginResponseFailed',
  LoginResponseSuccess = 'LoginResponseSuccess',
}
export enum TokenCode {
  accessToken = 'accessToken',
  refreshToken = 'refreshToken',
}
export interface LoginResponseBase {}
export interface LoginRequestType {
  userId: string
  password: string
}
export interface LoginResponseToken extends LoginResponseBase {
  type: LoginResponseCode.LoginResponseToken
  accessToken: string
  refreshToken: string
}
export interface LoginResponseFailed extends LoginResponseBase {
  type: LoginResponseCode.LoginResponseFailed
  msg: string
}
export interface LoginResponseSuccess extends LoginResponseBase {
  type: LoginResponseCode.LoginResponseSuccess
  id: string
  name: string
  profileImageUri?: string
  email: string
  mobile?: string
  signatureDocId?: string
  userStatusCode: string
}
// final String id;
// final String name;
// // @JsonKey(
// //   fromJson: DataUtils.pathToUrl,
// // )
// final String? profileImageUri;
// final String email;
// final String? mobile;
// final String? signatureDocId;

// final String userStatusCode;
export const isLoginResponceToken = (obj: LoginResponseBase) => {
  return LoginResponseCode.LoginResponseToken in obj
}
export const isLoginResponceSuccess = (obj: LoginResponseBase) => {
  return LoginResponseCode.LoginResponseSuccess in obj
}
