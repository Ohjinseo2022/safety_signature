'use client'

import NProgress from 'nprogress'
import { useEffect } from 'react'
import { usePathname, useRouter } from 'next/navigation'

const PageChangeMiddleware = ({
  children,
}: Readonly<{
  children: React.ReactNode
}>) => {
  const pathname = usePathname()
  const router = useRouter()

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
      console.log(args)
      handleRouteStart()
      await originalPush(...args)
      handleRouteComplete()
    }

    return () => {
      router.push = originalPush // 원래 메서드 복원
    }
  }, [pathname, router])

  return <>{children}</>
}

export default PageChangeMiddleware
