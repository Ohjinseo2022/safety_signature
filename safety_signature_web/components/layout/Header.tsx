'use client'

import styled, { css, keyframes } from 'styled-components'
import { useState } from 'react'
import Link from 'next/link'
import { useRouter } from 'next/navigation'

export default function Header() {
  const router = useRouter()
  const [visibleSubMenu, setVisibleSubMenu] = useState<number | null>(null)

  const menuItems: { label: string; path: string; subMenu: string[] }[] = [
    { label: '회원관리', path: '/user', subMenu: [] },
    { label: '안전문서관리', path: '/safety', subMenu: [] },
    { label: '현장관리', path: '/site', subMenu: [] },
  ]

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
