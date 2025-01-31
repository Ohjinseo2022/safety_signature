'use client'

import styled, { keyframes } from 'styled-components'
import { useRouter } from 'next/navigation'

interface CommonCommingSoonProps {}

const CommonComingSoon: React.FC<CommonCommingSoonProps> = ({}) => {
  const router = useRouter()

  return (
    <Container>
      <AnimationContainer>🚧</AnimationContainer>
      <Title>페이지 준비 중입니다</Title>
      <Description>
        현재 이 페이지는 개발 중입니다. 빠른 시일 내에 제공될 예정입니다.
      </Description>
      <BackButton onClick={() => router.push('/safety')}>
        홈으로 돌아가기
      </BackButton>
    </Container>
  )
}
export default CommonComingSoon

// 🚀 애니메이션 효과 (흔들리는 느낌 추가)
const shake = keyframes`
  0% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  50% { transform: translateX(5px); }
  75% { transform: translateX(-3px); }
  100% { transform: translateX(0); }
`

// ✅ 전체 컨테이너 스타일
const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100%;
  background-color: #121212;
  color: #e0e0e0;
  text-align: center;
  padding: 20px;
`

// ✅ 공사 아이콘 스타일 (흔들리는 애니메이션 추가)
const AnimationContainer = styled.div`
  font-size: 80px;
  animation: ${shake} 1s ease-in-out infinite;
  margin-bottom: 20px;
`

// ✅ 제목 스타일
const Title = styled.h1`
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #ffffff;
`

// ✅ 설명 스타일
const Description = styled.p`
  font-size: 16px;
  color: #b0b0b0;
  margin-bottom: 20px;
`

// ✅ 홈으로 돌아가기 버튼
const BackButton = styled.button`
  background-color: #60a5fa;
  color: #ffffff;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);

  &:hover {
    background-color: #3b82f6;
  }
`
