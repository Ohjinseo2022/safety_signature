'use client'

import { useLoadingStore } from '@/store/store'
import { useEffect, useReducer, useState } from 'react'
import { useRouter } from 'next/navigation'
import CommonCommingSoon from '@/components/common/CommonComingSoon'

interface MainPageProps {}

const MainPage = ({}: MainPageProps) => {
  const router = useRouter()
  const { isLoading, onLoading, offLoading } = useLoadingStore()
  const goTest = () => {
    router!.push('/test')
  }
  return <CommonCommingSoon />
}

export default MainPage
