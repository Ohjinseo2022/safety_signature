'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'

interface MainBodyProps {}
const MainContainer = styled.main`
  min-height: calc(100vh - 160px); /* 헤더(20px) + 푸터(20px) 제외한 높이 */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #1e1e1e; /* 메인 배경색 */
  color: #e0e0e0; /* 텍스트 색상 */
`
const MainBody = ({
  children,
}: Readonly<{
  children: React.ReactNode
}>) => {
  return <MainContainer>{children}</MainContainer>
}

export default MainBody
