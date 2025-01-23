'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'

interface CommonTextareaProps {
  id?: string
  value?: string
  onChange?: (e: any) => void
  placeholder?: string
}

const CommonTextarea: React.FC<CommonTextareaProps> = ({
  id,
  value,
  onChange,
  placeholder = '내용을 입력하세요',
}) => {
  return (
    <Textarea
      id={id}
      placeholder={placeholder}
      value={value}
      onChange={(e) => onChange?.(e.target.value)}
    ></Textarea>
  )
}

export default CommonTextarea

const Textarea = styled.textarea`
  min-height: 200px; /* 기본 높이 */
  resize: vertical; /* 크기 조절 가능 */
  padding: 12px;
  border-radius: 4px;
  border: 1px solid #444444;
  background-color: #1e1e1e;
  color: #e0e0e0;
  font-size: 14px;

  &::placeholder {
    color: #666666;
  }
`
