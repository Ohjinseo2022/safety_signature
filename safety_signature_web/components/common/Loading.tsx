'use client'

import ClipLoader from 'react-spinners/ClipLoader'
import styled from 'styled-components'
import { CSSProperties, useEffect, useState } from 'react'

const override: CSSProperties = {
  display: 'block',
  margin: '0 auto',
  // borderColor: "",
}

interface LoadingProps {}

const Loading = ({}: LoadingProps) => {
  let [loading, setLoading] = useState(true)
  return (
    <LoadingContainer>
      <ClipLoader
        color="#A1B3C9"
        loading={loading}
        cssOverride={override}
        size={100}
        aria-label="Loading Spinner"
        data-testid="loader"
      />
    </LoadingContainer>
  )
}

export default Loading

let LoadingContainer = styled.div`
  position: fixed; /* 화면 전체 덮기 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* 반투명 검정 배경 */
  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
  justify-content: center; /* 수평 가운데 정렬 */
  z-index: 9999; /* 최상위 z-index */
  pointer-events: auto;
`
