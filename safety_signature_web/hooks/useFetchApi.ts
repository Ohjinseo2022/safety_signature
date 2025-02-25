'use client'

import { getItem } from '@/store/localStorage'
import { extractQuotes } from '@/utils/regexpUtil'
// import { useLoadingStore } from '@/store/store'
import axios, { Method, ResponseType } from 'axios'
import { postTokenRefresh } from '@/app/(common)/user/login/_userRepository/tokenRepository'
import { TokenCode } from '@/app/(common)/user/login/_userRepository/types'

export const instance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
})
instance.interceptors.request.use(
  (config) => {
    //요청을 보내기 전에 수행할 로직
    return config
  },
  (error) => {
    //요청 에러가 발생했을 때 수행할 로직
    console.log(`[FetchApi] : request 오류 발생`)
    console.log(error)
    return Promise.reject(error)
  }
)
instance.interceptors.response.use(
  (response) => {
    //응답에 대한 로직 작성
    const etcObj: any = {}
    if (response.headers['x-total-count']) {
      etcObj.count = response.headers['x-total-count']
    }
    if (response.headers['content-disposition']) {
      const str = response.headers['content-disposition'] || ''
      etcObj.filename = extractQuotes(str) || ''
    }

    return { ...response, ...etcObj }
  },
  async (error) => {
    const {
      config,
      response: { status, message },
    } = error

    //응답 에러가 발생했을 때 수행할 로직
    console.log(
      `[FetchApi] : ${
        error.code.includes('REQUEST') ? 'request' : 'response'
      } 오류 발생`
    )
    const refreshToken = getItem({ key: TokenCode.refreshToken })
    const isPathRefresh = config.url.includes('token')
    if (!refreshToken || isPathRefresh) {
      return Promise.reject({ error: error.response })
    }
    if (status === 401) {
      console.log('토튼 만료 에러 토큰 리프레시 시도')
      // 기존 토큰이 만료 상태라면
      const originalRequset = config

      const isRefresh = await postTokenRefresh()
      if (!isRefresh) {
        return Promise.reject({ error: error.response })
      } else {
        originalRequset.headers.authorization = `Bearer ${getItem({ key: TokenCode.accessToken })}`
      }
      const res = await instance(originalRequset)
      return res
    }
    console.log(error.response)
    return Promise.reject({ error: error.response })
  }
)

const useFetchApi = async <T>(
  apiUrl: string,
  opts: {
    method: Method
    data?: { [key: string]: any } | any
    params?: any
    headers?: any
  },
  etc?: { isAuth?: boolean; responseType?: ResponseType }
) => {
  // const { onLoading, offLoading } = useLoadingStore() // 반드시 컴포넌트 내부에서 호출

  // onLoading()
  const headers = opts.headers
    ? { ...opts.headers }
    : { 'Content-Type': 'application/json' }

  return await instance<T>({
    method: opts.method,
    url: apiUrl,
    data: opts.data,
    responseType: etc?.responseType ? etc?.responseType : 'json',
    params: opts.params,
    headers:
      etc?.isAuth && getItem({ key: TokenCode.accessToken })
        ? {
            ...headers,
            authorization: `Bearer ${getItem({ key: TokenCode.accessToken })}`,
          }
        : headers,
  })
    .then((res) => {
      return res
    })
    .catch((err) => {
      return err
    })
    .finally(() => {})
}

export default useFetchApi
