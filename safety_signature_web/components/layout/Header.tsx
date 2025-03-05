'use client'

import { useAlertStore } from '@/store/alertStore'
import { removeItem } from '@/store/localStorage'
import { usePathParamStore } from '@/store/store'
import styled, { css, keyframes } from 'styled-components'
import { useEffect, useMemo, useState } from 'react'
import Link from 'next/link'
import { useParams, usePathname, useRouter } from 'next/navigation'
import {
  isLoginResponceSuccess,
  LoginResponseCode,
  LoginResponseSuccess,
  TokenCode,
} from '@/app/(common)/user/login/_userRepository/types'
import { useUserProfile } from '@/app/(common)/user/login/_userState/userStore'
import CommonButton from '../common/CommonButton'
import CommonModal from '../modal/CommonModal'

export default function Header() {
  const router = useRouter()
  const pathname = usePathname()
  const pathStore = usePathParamStore()
  const [visibleSubMenu, setVisibleSubMenu] = useState<number | null>(null)
  const { userProfile, initProfile } = useUserProfile()
  const menuItems: { label: string; path: string; subMenu: string[] }[] = [
    { label: '회원 관리', path: '/user', subMenu: [] },
    { label: '전자결제 관리', path: '/bulletin', subMenu: [] },
    { label: '현장 관리', path: '/site', subMenu: [] },
    // { label: '네이버 지도 API', path: '/navermaps', subMenu: [] },
  ]
  const [profile, setProfile] = useState<boolean>(false)
  useEffect(() => {
    //console.log(userProfile)
    //console.log('얍', isLoginResponceSuccess(userProfile))
    setProfile(isLoginResponceSuccess(userProfile))
  }, [pathname, userProfile])

  const { isModalVisible, onChangeModalVisible, children, callBackFunction } =
    useAlertStore()

  const onLoginAndOutHandler = async (type: string) => {
    if (type === 'o') {
      //로그아웃 로직 추가해야함.
      //console.log('저장해야할 마지막 URL : ', pathname)
      removeItem({ key: TokenCode.accessToken })
      removeItem({ key: TokenCode.refreshToken })
      pathStore.setLastPath(pathname, {})
      pathStore.setUseLastPath(true)
      initProfile()
    }
    router.push('/user/login')
  }
  return (
    <HeaderContainer>
      <Logo>
        <Link href={`${process.env.NEXT_PUBLIC_DOMAIN}/main`}>안전 싸인</Link>
      </Logo>
      <Nav>
        {menuItems.map((item, index) => (
          <li
            key={index}
            onMouseEnter={() => setVisibleSubMenu(index)}
            onMouseLeave={() => setVisibleSubMenu(null)}
          >
            <button
              onClick={() =>
                router.push(`${process.env.NEXT_PUBLIC_DOMAIN}${item.path}`)
              }
            >
              {item.label}
            </button>

            {item.subMenu.length > 0 && (
              <SubMenu $isVisible={visibleSubMenu === index}>
                {item.subMenu.length > 0
                  ? item.subMenu.map((subItem, subIndex) => (
                      <li key={subIndex}>
                        <a
                          href={`/${subItem.toLowerCase().replace(/ /g, '-')}`}
                        >
                          {subItem}
                        </a>
                      </li>
                    ))
                  : null}
              </SubMenu>
            )}
          </li>
        ))}
      </Nav>
      {profile ? (
        <Profile>
          <CommonButton onClick={() => onLoginAndOutHandler('o')}>
            로그아웃
          </CommonButton>
        </Profile>
      ) : (
        <Profile>
          <CommonButton onClick={() => onLoginAndOutHandler('i')}>
            로그인
          </CommonButton>
        </Profile>
      )}

      <div>
        <CommonModal
          isVisible={isModalVisible}
          title=""
          setIsVisible={(e) => {
            onChangeModalVisible({ isVisible: e })
          }}
          callBackFunction={callBackFunction}
        >
          {children}
        </CommonModal>
      </div>
    </HeaderContainer>
  )
}

// 나타나는 애니메이션
const slideDown = keyframes`
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
`

const slideUp = keyframes`
  from {
    opacity: 1;
    transform: translateY(0);
  }
  to {
    opacity: 0;
    transform: translateY(-10px);
  }
`

// 스타일드 컴포넌트
const HeaderContainer = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #121212; /* 다크 테마 배경색 */
  border-bottom: 1px solid #333333; /* 헤더 하단 경계선 */
  color: #ffffff;
`

const Nav = styled.nav`
  display: flex;
  justify-content: space-around; /* 메뉴를 가운데 정렬 */
  gap: 20px;
  list-style: none;
  flex: 1; /* 메뉴를 헤더 중앙으로 배치 */
  li {
    position: relative;
    cursor: pointer;
    &:hover {
      color: #60a5fa;
    }
    button {
      color: #ffffff;
      background-color: transparent;
      /* &:hover {
        color: #60a5fa;
      } */
    }
  }
`
interface SubMenuType {
  $isVisible: boolean
}
const SubMenu = styled.ul<SubMenuType>`
  position: absolute;
  top: 100%;
  left: 0;
  width: 200px;
  background-color: #1e1e1e;
  padding: 10px;
  border: 1px solid #333333;
  border-radius: 4px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  list-style: none;
  opacity: ${({ $isVisible }) => ($isVisible ? 1 : 0)};
  transform: ${({ $isVisible }) =>
    $isVisible ? 'translateY(0)' : 'translateY(-10px)'};
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
  z-index: 10;

  li {
    padding: 5px 10px;

    a {
      text-decoration: none;
      color: #60a5fa;

      &:hover {
        color: #3b82f6;
      }
    }
  }
`

const Logo = styled.h1`
  font-size: 36px;
  font-weight: bold;
  a {
    color: #ffffff;
    &:hover {
      color: #60a5fa;
    }
  }
`
const Profile = styled.div``
