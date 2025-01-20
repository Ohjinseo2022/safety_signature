'use client'

import {
  createGlobalStyle,
  ServerStyleSheet,
  StyleSheetManager,
  ThemeProvider,
} from 'styled-components'
import { ReactNode, useState } from 'react'

// 전역 스타일 정의
const GlobalStyle = createGlobalStyle`
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
  }

  a {
    text-decoration: none;
    color: #0070f3;

    &:hover {
      text-decoration: underline;
    }
  }
  body {
    margin: 0;
    padding: 0;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
    background-color: #f9f9f9;
  }

  a {
    text-decoration: none;
    color: #0070f3;
  }

  a:hover {
    text-decoration: underline;
  }
`

// 테마 정의
const theme = {
  colors: {
    primary: '#0070f3',
  },
}

export default function StyledComponentsRegistry({
  children,
}: {
  children: ReactNode
}) {
  const [sheet] = useState(() => new ServerStyleSheet())

  return (
    <StyleSheetManager sheet={sheet.instance}>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        {children}
      </ThemeProvider>
    </StyleSheetManager>
  )
}
