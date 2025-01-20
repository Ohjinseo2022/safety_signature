'use client'

import { useLoadingStore } from '@/store/store'
import { useEffect, useReducer, useState } from 'react'
import { useRouter } from 'next/navigation'

interface MainPageProps {}

const MainPage = ({}: MainPageProps) => {
  const router = useRouter()
  const { isLoading, onLoading, offLoading } = useLoadingStore()
  const goTest = () => {
    router!.push('/test')
  }
  return (
    <>
      해위잉
      <button
        onClick={() => {
          onLoading()
        }}
      >
        로딩떠라
      </button>
      <br />
      <br />
      <button
        onClick={() => {
          goTest()
        }}
      >
        이동쓰
      </button>
    </>
  )
}

export default MainPage
