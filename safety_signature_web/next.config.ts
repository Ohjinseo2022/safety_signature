import type { NextConfig } from 'next'

const nextConfig: NextConfig = {
  /* config options here */
  reactStrictMode: false,
  compiler: {
    styledComponents: {
      ssr: true,
      displayName: true,
    },
  },
  //타입스크립트 적용
  typescript: {
    ignoreBuildErrors: false,
    tsconfigPath: 'tsconfig.json',
  },
  distDir: '.next',
  async rewrites() {
    //redirects  rewrites
    return [
      {
        source: `${process.env.NEXT_PUBLIC_BASE_URL}/:path*`,
        destination: `${process.env.NEXT_PUBLIC_BACKEND_URL}${process.env.NEXT_PUBLIC_BASE_URL}/:path*`,
      },
    ]
  },
}

export default nextConfig
