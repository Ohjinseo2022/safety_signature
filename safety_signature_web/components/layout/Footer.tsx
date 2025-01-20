'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'

interface FooterProps {}
const FooterContainer = styled.footer`
  padding: 20px;
  background-color: #121212; /* 다크 테마 배경색 */
  border-top: 1px solid #333333; /* 푸터 상단 경계선 */
  color: #e0e0e0; /* 텍스트 색상 */
  text-align: center;
  font-size: 14px;

  a {
    color: #60a5fa; /* 링크 색상 */
    text-decoration: none;
    transition: color 0.3s ease;

    &:hover {
      color: #3b82f6; /* 호버 시 강조 색상 */
      text-decoration: underline;
    }
  }
`
const Footer = ({}: FooterProps) => {
  return (
    <FooterContainer>
      <p>
        © 안심 전자 결제 관리자 사이트 |{' '}
        <a href="/privacy">개인정보 처리 방침</a>
      </p>
    </FooterContainer>
  )
}

export default Footer
