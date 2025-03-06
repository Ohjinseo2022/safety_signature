'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'

interface CommonButtonProps {
  type?: 'button' | 'submit' | 'reset' | undefined
  onClick?: (e: any) => void
  children?: React.ReactNode
  disabled?: boolean
  $color?: string
  $backgroundColor?: string
}

const CommonButton = ({
  type = 'button',
  onClick,
  children,
  disabled = false,
  $color = '#ffffff',
  $backgroundColor,
}: CommonButtonProps) => {
  return (
    <Button
      type={type}
      disabled={disabled}
      onClick={onClick}
      $color={$color}
      $backgroundColor={$backgroundColor}
    >
      {children}
    </Button>
  )
}

export default CommonButton
const Button = styled.button<{ $color: string; $backgroundColor?: string }>`
  margin-right: 8px;
  background-color: ${({ $backgroundColor }) => $backgroundColor ?? `#60a5fa`};
  color: ${({ $color }) => $color ?? '#ffffff'}; //
  border: none;
  padding: 10px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 40px;

  &:hover {
    background-color: ${({ $backgroundColor }) =>
      $backgroundColor
        ? `rgba(${hexToRgb($backgroundColor)}, 0.8)`
        : '#3b82f6'};
  }
`
// HEX 값을 RGB로 변환하는 함수
const hexToRgb = (hex: any) => {
  let r = parseInt(hex.slice(1, 3), 16)
  let g = parseInt(hex.slice(3, 5), 16)
  let b = parseInt(hex.slice(5, 7), 16)
  return `${r}, ${g}, ${b}`
}
