'use client'

import styled from 'styled-components'
import { ReactNode } from 'react'

interface CommonModalProps {
  overlayClose?: boolean
  children: ReactNode | string // HTML 태그 또는 HTML 문자열 허용
  callBackFunction?: () => Promise<boolean>
  isVisible?: boolean
  isCancel?: boolean
  title?: string
  setIsVisible: (bool: boolean) => void
}

const CommonModal: React.FC<CommonModalProps> = ({
  overlayClose = true,
  isVisible = false,
  title,
  children,
  isCancel = false,
  setIsVisible,
  callBackFunction,
}) => {
  const onClose = () => {
    setIsVisible(false)
  }
  const onConfirm = async () => {
    const result = await callBackFunction?.()
    if (result) {
      setIsVisible(false)
    }
  }
  if (!isVisible) return null
  return (
    <ModalOverlay
      onClick={(e: any) => {
        if (overlayClose && e.target?.className.includes('ModalOverlay'))
          setIsVisible(false)
      }}
    >
      <ModalContainer>
        <ModalHeader>
          <h2>{title}</h2>
          <button onClick={onClose}>&times;</button>
        </ModalHeader>
        <ModalContent>
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

const ModalContainer = styled.div`
  background-color: #1e1e1e;
  color: #e0e0e0;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  animation: fadeIn 0.3s ease-in-out;

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

const ModalContent = styled.div`
  font-size: 16px;
  color: #b0b0b0;

  /* HTML 문자열을 처리할 수 있도록 스타일 정의 */
  p,
  div {
    margin: 10px 0;
  }
`

const ModalFooter = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
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
