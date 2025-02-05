'use client'

import { useEffect, useRef, useState } from 'react'

interface NaverMapsProps {
  latitude?: Lat
  longitude?: Lng
}

const NaverMaps: React.FC<NaverMapsProps> = ({
  latitude = 37.5665,
  longitude = 126.978,
}) => {
  const mapElement = useRef<HTMLDivElement | null>(null)
  const mapInstance = useRef<any>(null)
  const markerInstance = useRef<any>(null)
  const circleInstance = useRef<any>(null)
  const [latLng, setLatLng] = useState<{ latitude: Lat; longitude: Lng }>({
    latitude: 37.5665,
    longitude: 126.978,
  })
  useEffect(() => {
    const scriptId = 'naver-map-script'
    if (mapElement != null) {
      const script = document.createElement('script')
      script.id = scriptId
      script.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_MAP_CLIENT_ID}&language=ko`
      script.async = true
      script.onload = () => {
        if (window.naver && mapElement?.current) {
          const map = new naver.maps.Map(mapElement?.current, {
            center: new naver.maps.LatLng(latitude, longitude), // 서울 좌표
            zoom: 13, //지도의 초기 줌 레벨
            minZoom: 7, //지도의 최소 줌 레벨
            zoomControl: true, //줌 컨트롤의 표시 여부
            zoomControlOptions: {
              //줌 컨트롤의 옵션
              position: naver.maps.Position.TOP_RIGHT,
            },
          })
          mapInstance.current = map
          // map.setOptions()
          // 마커 추가
          markerInstance.current = new naver.maps.Marker({
            position: new naver.maps.LatLng(latitude, longitude),
            map,
          })
          //원 추가
          // map?: Map;
          // center: Coord | CoordLiteral;
          // radius?: number;
          // strokeWeight?: number;
          // strokeOpacity?: number;
          // strokeColor?: string;
          // strokeStyle?: StrokeStyleType;
          // strokeLineCap?: StrokeLineCapType;
          // strokeLineJoin?: StrokeLineJoinType;
          // fillColor?: string;
          // fillOpacity?: number;
          // clickable?: boolean;
          // visible?: boolean;
          // zIndex?: number;
          circleInstance.current = new naver.maps.Circle({
            map: mapInstance.current,
            center: new naver.maps.LatLng(latLng!.latitude, latLng!.longitude),
            radius: 100,
            fillColor: 'red',
            fillOpacity: 0.4,
            // visible: false,
          })
        }
      }
      document.body.appendChild(script)
    }
    return () => {
      const script = document.getElementById(scriptId)
      if (script) {
        script.remove()
      }
    }
  }, [])
  const onChangeInstance = async (e: any) => {
    setLatLng((_) => {
      if (markerInstance.current && mapInstance.current) {
        const newPosition = new window.naver.maps.LatLng(
          latLng!.latitude,
          latLng!.longitude
        )
        markerInstance.current.setPosition(newPosition) // 마커 위치 변경
        mapInstance.current.setCenter(newPosition) // 지도의 중심 이동
        circleInstance.current.setCenter(newPosition) // 중심에 맞게 원 이동 시키기
      }
      return {
        latitude: latLng.latitude + 0.001,
        longitude: latLng.longitude + 0.001,
      }
    })
    // setMarkerPosition({ lat: newLat, lng: newLng })
  }
  return (
    <>
      <button onClick={onChangeInstance}>클릭시 위치가변경됨</button>
      <div ref={mapElement} style={{ width: '100vh', height: '80vh' }} />
    </>
  )
}

export default NaverMaps
