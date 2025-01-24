'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'

interface CommonButtonProps {
  type: 'button' | 'submit' | 'reset' | undefined
  onClick?: (e: any) => void
  children?: React.ReactNode
  disabled?: boolean
  $color?: string
}

const CommonButton = ({
  type = 'button',
  onClick,
  children,
  disabled = false,
  $color = '#ffffff',
}: CommonButtonProps) => {
  return (
    <Button type={type} disabled={disabled} onClick={onClick} $color={$color}>
      {children}
    </Button>
  )
}

export default CommonButton
const Button = styled.button<{ $color: string }>`
  background-color: #60a5fa;
  color: ${({ $color }) => $color ?? '#ffffff'}; //
  border: none;
  padding: 10px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 40px;

  &:hover {
    background-color: #3b82f6;
  }
`
