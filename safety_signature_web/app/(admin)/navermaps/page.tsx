'use client'

import { useEffect, useRef, useState } from 'react'
import NaverMaps from '@/components/externalScripts/NaverMaps'

interface NaverMapsPageProps {}

const NaverMapsPage: React.FC<NaverMapsPageProps> = ({}) => {
  const maps = useRef<any>(null)
  //   useEffect(() => {
  //     maps.current = new naver.maps.Map('map', {
  //       center: new naver.maps.LatLng(37.3595704, 127.105399),
  //       zoom: 10,
  //     })
  //     // maps.current = new
  //   }, [])
  return (
    <div>
      <h1>네이버 지도를 그려줭</h1>
      <NaverMaps></NaverMaps>
    </div>
  )
}

export default NaverMapsPage
