'use client'

import { useAlertStore } from '@/store/alertStore'
import { useLoadingStore } from '@/store/store'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import useFetchApi from '@/hooks/useFetchApi'
import { useInput } from '@/hooks/useInput'
import CommonButton from '@/components/common/CommonButton'
import CommonInput from '@/components/common/CommonInput'
import { postLogin } from './_userRepository/loginRepository'
import {
  isLoginResponceSuccess,
  LoginResponseBase,
  TokenCode,
} from './_userRepository/types'
import { useUserProfile } from './_userState/userStore'

interface UserLoginProps {}

const UserLogin: React.FC<UserLoginProps> = ({}) => {
  const [userId, onChangeUserId, setUserId] = useInput('')
  const [password, onChangePassword, setPassword] = useInput('')
  const [btnDisabled, onChangeBtnDisabled, setBtnDisabled] = useInput(false)
  const { userProfile: userInfo } = useUserProfile()
  const { isLoading } = useLoadingStore()
  const { isModalVisible, onChangeModelVisible } = useAlertStore()
  const router = useRouter()
  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    setBtnDisabled(true)
    if (!btnDisabled) {
      if (!userId) {
        onChangeModelVisible({ isVisible: true, msg: '아이디를 입력해주세요' })
        return
      }
      if (!password) {
        onChangeModelVisible({
          isVisible: true,
          msg: '비밀번호를 입력해주세요',
        })
        return
      }
      const userProfile = await postLogin({
        userId: userId,
        password: password,
      })
      if (isLoginResponceSuccess(userProfile)) {
        router.push('/main')
      }
    }
    //// 서버통신 완료 이후
    setBtnDisabled(false)
  }
  //중복 클릭 방지를위한 버튼 활성화 및 비활성화
  useEffect(() => {
    setBtnDisabled(isModalVisible)
  }, [isModalVisible])

  if (isLoginResponceSuccess(userInfo)) {
    return <></>
  } else {
    console.log(userInfo)
    return (
      <LoginContainer>
        <LoginBox>
          <Title>로그인</Title>
          <form onSubmit={handleLogin}>
            <FormGroup>
              <CommonInput
                htmlFor="아이디"
                label="아이디"
                placeholder="이메일형식의 아이디를 입력하세요."
                type="email"
                value={userId}
                onChange={onChangeUserId}
              />
            </FormGroup>
            <FormGroup>
              <CommonInput
                htmlFor="비밀번호"
                label="비밀번호"
                placeholder="비밀번호를 입력하세요."
                type="password"
                value={password}
                onChange={onChangePassword}
              />
            </FormGroup>
            <br />
            <CommonButton type="submit" disabled={btnDisabled}>
              로그인
            </CommonButton>
          </form>
          <ForgotPassword href="/forgot-password">
            비밀번호를 잊으셨나요?
          </ForgotPassword>
        </LoginBox>
      </LoginContainer>
    )
  }
}

export default UserLogin

const LoginContainer = styled.div`
  background-color: #121212;
  color: #e0e0e0;
  height: 70vh; //100vh;
  width: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

const LoginBox = styled.div`
  background-color: #1e1e1e;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  gap: 20px;
`

const Title = styled.h2`
  font-size: 24px;
  margin: 0;
  color: #ffffff;
  text-align: center;
`

const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  label {
    font-size: 14px;
    color: #b0b0b0;
  }

  input {
    padding: 12px;
    border-radius: 4px;
    border: 1px solid #444444;
    background-color: #2a2a2a;
    color: #e0e0e0;
    font-size: 14px;

    &::placeholder {
      color: #666666;
    }
  }
`

const ForgotPassword = styled.a`
  text-align: center;
  font-size: 14px;
  color: #60a5fa;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
    color: #3b82f6;
  }
`
