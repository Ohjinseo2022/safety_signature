import { setItem } from '@/store/localStorage'
import { useQuery } from '@tanstack/react-query'
import useFetchApi from '@/hooks/useFetchApi'
import {
  LoginRequestType,
  LoginResponseBase,
  LoginResponseCode,
  TokenCode,
} from './types'

export const postLogin = async (props: LoginRequestType) => {
  const { data, status, error } = await useFetchApi<LoginResponseBase>(
    '/login/normal',
    {
      method: 'post',
      data: { ...props },
    }
  )
  if (status === 200) {
    setItem({ key: TokenCode.accessToken, item: data[TokenCode.accessToken] })
    setItem({ key: TokenCode.refreshToken, item: data[TokenCode.refreshToken] })

    return await getUserProfile()
  }
  if (status === 204) {
    return {
      msg: '아이디 또는 패스워드를 확인해주세요.',
      type: LoginResponseCode.LoginResponseFailed,
    }
  }
  return {
    msg: error.toString(),
    type: LoginResponseCode.LoginResponseFailed,
  }
}
export const getUserProfile = async () => {
  const { data, status, error } = await useFetchApi(
    '/user/profile',
    {
      method: 'get',
    },
    { isAuth: true }
  )
  if (status === 200) {
    return { ...data, type: LoginResponseCode.LoginResponseSuccess }
  } else {
    return { ...error, type: LoginResponseCode.LoginResponseFailed }
  }
}
/**
 * 상태를 관리할 필요가 없음 ! ! ! ! ! !!
 */
export const useLoginQuery = (props: LoginRequestType) => {
  return useQuery({
    queryKey: ['loginFetch'],
    queryFn: async () => postLogin(props),
    enabled:
      typeof props.userId === 'string' && typeof props.password === 'string',
    gcTime: 0, // 30분
  })
}
