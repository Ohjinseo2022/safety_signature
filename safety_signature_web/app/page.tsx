'use client'

import { useLoadingStore } from '@/store/store'
import { useEffect } from 'react'
import { useRouter } from 'next/navigation'

export default function Home() {
  const { onLoading, offLoading } = useLoadingStore()
  const router = useRouter()
  useEffect(() => {
    onLoading()
    setTimeout(async () => {
      offLoading()
      router.push('/main')
    }, 1000)
  }, [])
  return (
    <div
      style={{
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        backgroundColor: 'rgba(0, 0, 0, 1)', // 반투명 배경
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 999, // 모든 요소 위로
        color: '#fff', // 글자 색상
      }}
    ></div>
  )
}
