'use client'

import styled from 'styled-components'
import { useCallback } from 'react'
import { getMinIoFileDownload } from '@/hooks/common/useDownLoad'

interface CustomDownloadButtonProps {
  fileUrl?: string
  disabled?: boolean
  file: AttachDocMasterType
  onClick?: (e: any) => void
}

const CustomDownloadButton = ({
  fileUrl,
  disabled = false,
  file,
  onClick,
}: CustomDownloadButtonProps) => {
  const handleDownload = useCallback(
    (e: any) => {
      onClick?.(e)
      getMinIoFileDownload(file.id)
    },
    [onClick]
  )

  return (
    <DownloadButton onClick={handleDownload} disabled={disabled}>
      📥 {file?.attachDocName}
    </DownloadButton>
  )
}

export default CustomDownloadButton

const DownloadButton = styled.button`
  background-color: transparent !important;
  color: #ffffff;
  border: none;
  padding: 10px 16px;
  border-radius: 4px;
  cursor: pointer;
  text-decoration: underline;
  transition: background-color 0.3s ease;
  height: 40px;
  /* display: flex; */
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: bold;

  &:hover {
    background-color: #3b82f6;
  }

  &:disabled {
    background-color: #a0aec0;
    cursor: not-allowed;
  }
`
