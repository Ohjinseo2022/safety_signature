import type { Metadata } from 'next'
import { Geist, Geist_Mono } from 'next/font/google'
import './globals.css'
import PageChangeMiddleware from '@/middleware/pageChangeMiddleware'
import ReactQueryProviders from '@/hooks/useReactQuery'
import Loading from '@/components/common/Loading'
import Footer from '@/components/layout/Footer'
import Header from '@/components/layout/Header'
import MainBody from '@/components/layout/MainBody'

const geistSans = Geist({
  variable: '--font-geist-sans',
  subsets: ['latin'],
})

const geistMono = Geist_Mono({
  variable: '--font-geist-mono',
  subsets: ['latin'],
})
export const metadata: Metadata = {
  title: 'Create Next App',
  description: 'Generated by create next app',
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <PageChangeMiddleware>
          <ReactQueryProviders>
            <Loading />
            <Header />
            <MainBody>{children}</MainBody>
            <Footer />
          </ReactQueryProviders>
        </PageChangeMiddleware>
      </body>
    </html>
  )
}
