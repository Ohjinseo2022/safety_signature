'use client'

import { useLoadingStore } from '@/store/store'
import { HashLoader } from 'react-spinners'
import ClipLoader from 'react-spinners/ClipLoader'
import styled, { keyframes } from 'styled-components'
import { CSSProperties, useEffect, useState } from 'react'

const override: CSSProperties = {
  display: 'block',
  margin: '0 auto',
  // borderColor: "",
}

interface LoadingProps {}

const Loading = ({}: LoadingProps) => {
  const isLoading = useLoadingStore((state) => state.isLoading) // zustand 상태 구독

  if (!isLoading) return null // 로딩창 숨김

  return (
    <LoadingContainer>
      <LoadingContent>
        <HashLoader
          color="#A1B3C9"
          loading={isLoading}
          cssOverride={override}
          size={50}
          aria-label="Loading Spinner"
          data-testid="loader"
        />

        <LoadingDots>Loading</LoadingDots>
      </LoadingContent>
    </LoadingContainer>
  )
}

export default Loading

let LoadingContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
`
// 애니메이션 정의
const dotsAnimation = keyframes`
  0% {
    content: '';
  }
  33% {
    content: '.';
  }
  66% {
    content: '..';
  }
  100% {
    content: '...';
  }
`

/* 로딩 컴포넌트 내부 컨텐츠 */
let LoadingContent = styled.div`
  display: flex;
  flex-direction: column; /* 세로 정렬 */
  align-items: center; /* 가로 중앙 정렬 */
  justify-content: center; /* 세로 중앙 정렬 */
  color: white;
`
let LoadingDots = styled.span`
  /* font-size: 1.5rem; */
  padding-top: 0.5em;
  font-weight: bold;
  margin-left: 2em;
  display: inline-block;

  /* 점의 공간 고정 */
  &:after {
    display: inline-block;
    width: 2em; /* 점의 최대 길이만큼 공간 확보 */
    text-align: left;
    content: '';
    animation: ${dotsAnimation} 1.5s steps(3, end) infinite;
  }
`
