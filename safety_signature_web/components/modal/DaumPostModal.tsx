'use client'

import DaumPostcode from 'react-daum-postcode'
import { ReactNode, useEffect, useState } from 'react'
import CommonModal from './CommonModal'

interface DaumPostProps {
  overlayClose?: boolean
  onCompletePost?: (e: any) => void
  isVisible?: boolean
  setIsVisible: (bool: boolean) => void
}

const DaumPostModal: React.FC<DaumPostProps> = ({
  overlayClose = true,
  isVisible = false,
  setIsVisible,
  onCompletePost,
}) => {
  const onComplete = (data: any) => {
    onCompletePost?.(data)
    setIsVisible(false)
  }
  return (
    <CommonModal
      children={
        <div style={{ width: '100%', height: '80%' }}>
          <DaumPostcode
            style={{ width: '100%', height: '100%' }}
            onComplete={onComplete}
          />
        </div>
      }
      overlayClose={overlayClose}
      isVisible={isVisible}
      title={'주소 검색'}
      isCancel={true}
      setIsVisible={setIsVisible}
      width="100vw"
      height="90%"
    ></CommonModal>
  )
}

export default DaumPostModal
