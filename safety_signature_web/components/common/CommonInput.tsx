'use client'

import styled from 'styled-components'
import { HTMLInputTypeAttribute, useEffect, useState } from 'react'

interface CommonInputProps {
  type?: HTMLInputTypeAttribute
  placeholder?: string
  htmlFor?: string
  value: any
  onChange?: (e: any) => void
  label?: string
  isEnter?: boolean
  readOnly?: boolean
  onKeydown?: (e: any) => void
}

const CommonInput = ({
  type = 'string',
  placeholder,
  htmlFor,
  value,
  label,
  isEnter = false,
  readOnly,
  onChange,
}: CommonInputProps) => {
  return (
    <DefaultInputField>
      {label ? <Label htmlFor={htmlFor}>{label}</Label> : null}
      <Input
        id={htmlFor}
        placeholder={placeholder}
        type={type}
        value={value}
        onChange={onChange}
        readOnly={readOnly}
        // onKeyDown={(e) => {
        //   isEnter && e.key.toLowerCase() === 'enter' ? onKeydown : null
        // }}
      ></Input>
    </DefaultInputField>
  )
}

export default CommonInput

const Input = styled.input`
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #444444;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  height: 40px;

  &::placeholder {
    color: #666666;
  }
`

const Label = styled.label`
  margin-bottom: 8px;
  color: #b0b0b0;
  font-size: 14px;
`
const DefaultInputField = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  min-width: 200px;
`
