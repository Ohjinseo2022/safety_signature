'use client'

import styled from 'styled-components'
import { useCallback } from 'react'

interface CustomDownloadButtonProps {
  fileName: string
  fileUrl?: string
  disabled?: boolean
  onClick?: (e: any) => void
}

const CustomDownloadButton = ({
  fileName,
  fileUrl,
  disabled = false,
  onClick,
}: CustomDownloadButtonProps) => {
  const handleDownload = useCallback(
    (e: any) => {
      onClick?.(e)
    },
    [onClick]
  )

  return (
    <DownloadButton onClick={handleDownload} disabled={disabled}>
      📥 {fileName}
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
