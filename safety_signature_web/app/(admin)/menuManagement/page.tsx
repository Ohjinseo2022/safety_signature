'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'
import CommonButton from '@/components/common/CommonButton'
import {
  CommonCard,
  CommonContainer,
  CommonTitle,
} from '@/components/styled/common'

interface MenuManagementPageProps {}

const MenuManagementPage: React.FC<MenuManagementPageProps> = ({}) => {
  return (
    <CommonContainer>
      <CommonTitle>
        <h1>메뉴 관리</h1>
        <CommonButton>신규메뉴</CommonButton>
      </CommonTitle>
      <AreaContainer>
        <CommonCard></CommonCard>
        <CommonCard></CommonCard>
      </AreaContainer>
    </CommonContainer>
  )
}

export default MenuManagementPage

const AreaContainer = styled.div`
  width: 100%; /* 컨테이너 전체 너비 사용 */
  height: 100%; /* 부모 컨테이너의 높이 꽉 채우기 */
  display: flex;

  gap: 10px; /* 카드 사이의 간격 */
  min-height: calc(100vh - 80px); /* 최소 높이 설정 */
  div {
    padding: 20px;
  }
  div:first-child {
    flex: 1;
    margin-right: 20px;
  }
  div:nth-child(2) {
    flex: 2;
  }
`
