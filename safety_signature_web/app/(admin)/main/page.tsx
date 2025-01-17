'use client'

import { useLoadingStore } from '@/store/store'
import { useEffect, useState } from 'react'

interface MainPageProps {}

const MainPage = ({}: MainPageProps) => {
  const { isLoading, onLoading, offLoading } = useLoadingStore()
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
    </>
  )
}

export default MainPage
