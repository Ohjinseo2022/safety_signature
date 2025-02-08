'use client'

import { useAlertStore } from '@/store/alertStore'
import { setItem } from '@/store/localStorage'
import { useLoadingStore, usePathParamStore } from '@/store/store'
import NProgress from 'nprogress'
import { useEffect } from 'react'
import { NavigateOptions } from 'next/dist/shared/lib/app-router-context.shared-runtime'
import { usePathname, useRouter } from 'next/navigation'
import { getUserProfile } from '@/app/(common)/user/login/_userRepository/loginRepository'
import {
  isLoginResponceSuccess,
  LoginResponseSuccess,
  TokenCode,
  UserTypeCode,
} from '@/app/(common)/user/login/_userRepository/types'
import { useUserProfile } from '@/app/(common)/user/login/_userState/userStore'

const PageChangeMiddleware = ({
  children,
}: Readonly<{
  children: React.ReactNode
}>) => {
  const pathname = usePathname()
  const router = useRouter()
  const pathStore = usePathParamStore()
  const alertStore = useAlertStore()
  const userProfileStore = useUserProfile()
  const loading = useLoadingStore()
  // `router.push`와 같은 메서드 호출 감지 가능
  const handleRouteStart = async () => {
    console.log('페이지 이동 시작')

    NProgress.start()
  }

  // 페이지 이동 완료 감지
  const handleRouteComplete = () => {
    console.log('페이지 이동 완료')
    loading.offLoading()
    NProgress.done()
  }
  useEffect(() => {
    console.log('현재 경로:', pathname)

    alertStore.overlayClose = false
    // 페이지 이동 시작 감지
    // 📌 원래의 router 메서드를 저장
    const originalPush = router.push
    const originalReplace = router.replace
    const originalBack = router.back
    const originalForward = router.forward
    const originalRefresh = router.refresh
    const originalPrefetch = router.prefetch
    // 🔹 모든 네비게이션 메서드를 감싸는 공통 핸들러
    const handleNavigation = async (method: Function, ...args: any) => {
      const userProfile = await getUserProfile()
      const isLogin = isLoginResponceSuccess(userProfile)
      console.log(window.history)
      if (isLogin && pathname?.includes('login')) {
        if (window.history.length > 1) {
          // 📌 이전 페이지가 존재하면 뒤로 가기
          router.back()
        } else {
          // 📌 이전 페이지가 없으면 메인 페이지로 이동
          router.replace('/main')
        }
      }
      await handleRouteStart()

      const lastUrl: string = args[0]

      if (!pathStore.useLastPath) pathStore.setLastPath(args[0], args[1] || {})

      if (!pathname?.includes('login')) {
        if (
          !isLogin ||
          userProfile.userTypeCode === UserTypeCode.GENERAL_MEMBER
        ) {
          pathStore.useLastPath = true
          setItem({ key: TokenCode.accessToken, item: 'Expired' })
          setItem({ key: TokenCode.refreshToken, item: 'Expired' })
          alertStore.overlayClose = false
          alertStore.onChangeModalVisible({
            msg: '접근권한이 없습니다.',
            isVisible: true,
            callBackFunction: () => {
              originalPush('/user/login', args[1])
              return true
            },
          })
        } else {
          userProfileStore.setProfile(userProfile as LoginResponseSuccess)
          if (pathStore.useLastPath) {
            pathStore.useLastPath = false
            originalPush(
              pathStore.lastPath[0],
              pathStore.lastPath[1] as NavigateOptions
            )
          } else {
            method(...args) // 📌 원래의 메서드를 실행
          }
        }
      } else {
        method(...args) // 📌 원래의 메서드를 실행
      }
      handleRouteComplete()
    }
    // 📌 `router.push`, `router.replace`, `router.back`, `router.forward`, `router.refresh`, `router.prefetch` 오버라이드
    router.push = (...args) => handleNavigation(originalPush, ...args)
    router.replace = (...args) => handleNavigation(originalReplace, ...args)
    router.back = () => handleNavigation(originalBack)
    router.forward = () => handleNavigation(originalForward)
    router.refresh = () => handleNavigation(originalRefresh)
    router.prefetch = (...args) => handleNavigation(originalPrefetch, ...args)

    handleNavigation(() => originalPush)
    // ✅ 이벤트 중복 등록 방지
    // window.removeEventListener('popstate', handlePopState)
    // window.addEventListener('popstate', handlePopState)
    // ✅ 화면 초기 진입 시 `handleNavigation` 실행
    return () => {
      router.push = originalPush
      router.replace = originalReplace
      router.back = originalBack
      router.forward = originalForward
      router.refresh = originalRefresh
      router.prefetch = originalPrefetch
      // window.removeEventListener('popstate', handlePopState)
    }
  }, [pathname, router])

  return <>{children}</>
}

export default PageChangeMiddleware
