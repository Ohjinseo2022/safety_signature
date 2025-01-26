'use client'

import { usePathParamStore } from '@/store/store'
import NProgress from 'nprogress'
import { useEffect } from 'react'
import { usePathname, useRouter } from 'next/navigation'
import { getUserProfile } from '@/app/(common)/user/login/_repository/loginRepository'
import { isLoginResponceSuccess } from '@/app/(common)/user/login/_repository/types'

const PageChangeMiddleware = ({
  children,
}: Readonly<{
  children: React.ReactNode
}>) => {
  const pathname = usePathname()
  const router = useRouter()
  const pathStore = usePathParamStore()

  useEffect(() => {
    console.log('현재 경로:', pathname)

    // 페이지 이동 시작 감지
    const handleRouteStart = async () => {
      console.log('페이지 이동 시작')

      NProgress.start()
    }

    // 페이지 이동 완료 감지
    const handleRouteComplete = () => {
      console.log('페이지 이동 완료')

      NProgress.done()
    }

    // `router.push`와 같은 메서드 호출 감지 가능
    const originalPush = router.push
    router.push = async (...args) => {
      /**
      1. 현재 이동할페이지가 / user / login 이 아니라면 
      2. 유저 프로필정보를 조회해 와야한다. 
      3. 유저정보가 유효하지 않다면 접근권한 없음 알림창을 보여주며 로그인 창으로 이동 시켜준다.
      4. 마지막 이동해야했던 url 정보는 따로 스토어에 저장해준다.
      */
      await handleRouteStart()
      const lastUrl: string = args[0]
      if (!lastUrl.includes('login')) {
        //관리자 페이지는 로그인한 사용자만 접근가능 함
        const userProfile = await getUserProfile()
        console.log(userProfile)
        if (!isLoginResponceSuccess(userProfile)) {
          originalPush('/user/login', args[1])
        } else {
          await originalPush(...args)
        }
      } else {
        await originalPush(...args)
      }
      handleRouteComplete()
    }

    return () => {
      router.push = originalPush // 원래 메서드 복원
    }
  }, [pathname, router])

  return <>{children}</>
}

export default PageChangeMiddleware
