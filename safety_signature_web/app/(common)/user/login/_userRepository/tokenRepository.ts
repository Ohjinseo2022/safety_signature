import { getItem, setItem } from '@/store/localStorage'
import useFetchApi from '@/hooks/useFetchApi'
import { TokenCode } from './types'

export const postTokenRefresh = async () => {
  const { data, error, status } = await useFetchApi('/auth/token/renew', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
      authorization: `Bearer ${getItem({ key: TokenCode.refreshToken })}`,
    },
  })
  if (status === 200) {
    setItem({
      key: TokenCode.accessToken,
      item: data[TokenCode.accessToken],
    })
    setItem({
      key: TokenCode.refreshToken,
      item: data[TokenCode.refreshToken],
    })
    return true
  } else {
    return false
  }
}
