'use client'

import styled from 'styled-components'
import { ReactNode, useEffect } from 'react'

interface CommonModalProps {
  overlayClose?: boolean
  children: ReactNode | string // HTML 태그 또는 HTML 문자열 허용
  callBackFunction?: () => Promise<boolean>
  isVisible?: boolean
  isCancel?: boolean
  title?: string
  setIsVisible: (bool: boolean) => void
  width?: string // 모달 가로 크기
  height?: string // 모달 세로 크기
}

const CommonModal: React.FC<CommonModalProps> = ({
  overlayClose = true,
  isVisible = false,
  title,
  children,
  isCancel = false,
  setIsVisible,
  callBackFunction,
  width = '250px',
  height = '300px',
}) => {
  const onClose = () => {
    setIsVisible(false)
  }
  const onConfirm = async () => {
    if (callBackFunction) {
      const result = await callBackFunction?.()
      if (result) {
        setIsVisible(false)
      }
    } else {
      setIsVisible(false)
    }
  }
  // ESC 키로 모달 닫기
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape' || e.key === 'Enter') {
        setIsVisible(false)
      }
    }
    if (isVisible) {
      window.addEventListener('keydown', handleKeyDown)
    }
    return () => {
      window.removeEventListener('keydown', handleKeyDown)
    }
  }, [isVisible])

  const isTextOnly = typeof children === 'string'

  if (!isVisible) return null
  return (
    <ModalOverlay
      onClick={(e: any) => {
        if (overlayClose && e.target?.className.includes('ModalOverlay'))
          setIsVisible(false)
      }}
    >
      <ModalContainer $height={height} $isTextOnly={isTextOnly} $width={width}>
        {title && (
          <ModalHeader>
            <h2>{title}</h2>
            <button onClick={onClose}>&times;</button>
          </ModalHeader>
        )}
        <ModalContent $isTextOnly={isTextOnly}>
          {/* children을 렌더링 (태그 또는 HTML 문자열 모두 가능) */}
          {typeof children === 'string' ? (
            <div dangerouslySetInnerHTML={{ __html: children }} />
          ) : (
            children
          )}
        </ModalContent>
        <ModalFooter>
          {/* 조건부로 취소 버튼 표시 */}
          {isCancel && (
            <button className="cancel" onClick={onClose}>
              취소
            </button>
          )}
          <button onClick={onConfirm}>확인</button>
        </ModalFooter>
      </ModalContainer>
    </ModalOverlay>
  )
}

export default CommonModal

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`

const ModalContainer = styled.div<{
  $width: string
  $height: string
  $isTextOnly: boolean
}>`
  background-color: #1e1e1e;
  color: #e0e0e0;
  border-radius: 8px;
  width: ${({ $width }) => $width};
  max-width: 90%; /* 화면에 꽉 차지 않도록 제한 */
  height: ${({ $isTextOnly, $height }) => ($isTextOnly ? 'auto' : $height)};
  max-height: 90%; /* 세로 크기 제한 */
  padding: ${({ $isTextOnly }) => ($isTextOnly ? '40px 20px' : '20px')};
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  animation: fadeIn 0.2s ease-in-out;

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: scale(0.9);
    }
    to {
      opacity: 1;
      transform: scale(1);
    }
  }
`

const ModalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  h2 {
    font-size: 20px;
    margin: 0;
  }

  button {
    background: none;
    border: none;
    color: #ffffff;
    font-size: 20px;
    cursor: pointer;
    transition: color 0.3s ease;

    &:hover {
      color: #ff4d4f;
    }
  }
`

const ModalContent = styled.div<{ $isTextOnly: boolean }>`
  font-size: ${({ $isTextOnly }) => ($isTextOnly ? '18px' : '16px')};
  color: ${({ $isTextOnly }) => ($isTextOnly ? '#ffffff' : '#b0b0b0')};
  text-align: ${({ $isTextOnly }) => ($isTextOnly ? 'center' : 'left')};
  line-height: ${({ $isTextOnly }) => ($isTextOnly ? '1.8' : '1.6')};
  flex: 1;
  overflow-y: auto; /* 내용이 많을 경우 스크롤 */
  margin-bottom: 20px;
  /* HTML 문자열을 처리할 수 있도록 스타일 정의 */
  p,
  div {
    margin: 10px 0;
    font-weight: bold;
  }
`

const ModalFooter = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;

  button {
    background-color: #60a5fa;
    color: #ffffff;
    border: none;
    padding: 10px 16px;
    border-radius: 4px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #3b82f6;
    }

    &.cancel {
      background-color: #333333;

      &:hover {
        background-color: #444444;
      }
    }
  }
`
