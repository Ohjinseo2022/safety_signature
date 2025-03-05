'use client'

import { useEffect, useRef, useState } from 'react'

interface NaverMapsProps {
  latitude?: number
  longitude?: number
}

const NaverMaps: React.FC<NaverMapsProps> = ({
  latitude = 37.5665,
  longitude = 126.978,
}) => {
  const mapElement = useRef<HTMLDivElement | null>(null)
  const mapInstance = useRef<any>(null)
  const markerInstance = useRef<any>(null)
  const circleInstance = useRef<any>(null)
  const [latLng, setLatLng] = useState<{ latitude: number; longitude: number }>(
    {
      latitude,
      longitude,
    }
  )
  const [address, setAddress] = useState('') // 주소 저장

  // 📌 네이버 지도 역 지오코딩 API 호출
  const getAddressFromCoords = (lat: number, lng: number) => {
    if (!window.naver || !window.naver.maps.Service) {
      console.error('네이버 지도 API가 로드되지 않았습니다.')
      return
    }

    window.naver.maps.Service.reverseGeocode(
      {
        coords: new window.naver.maps.LatLng(lat, lng),
      },
      function (status: any, response: any) {
        if (status === window.naver.maps.Service.Status.OK) {
          const result = response.v2
          if (result && result.address) {
            //console.log(result)
            setAddress(
              result.address.jibunAddress || result.address.roadAddress
            )
          } else {
            setAddress('주소 정보를 가져올 수 없습니다.')
          }
        } else {
          setAddress('역 지오코딩 요청 실패')
        }
      }
    )
  }
  // useEffect(() => {
  //   if (address) {
  //     // window.naver.maps.
  //     window.naver.maps.searchKeyword(
  //       {
  //         query: '편의점',
  //       },
  //       (status: any, response: any) => {
  //         //console.log(status)
  //         //console.log(response)
  //       }
  //     )
  //   }
  // }, [address])
  useEffect(() => {
    const scriptId = 'naver-map-script'
    if (document.getElementById(scriptId)) {
      initMap()
      return
    }

    const script = document.createElement('script')
    script.id = scriptId
    script.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_MAP_CLIENT_ID}&language=ko&submodules=geocoder`
    script.async = true

    script.onload = () => {
      if (window.naver && window.naver.maps) {
        initMap()
      } else {
        console.error('네이버 지도 API를 로드할 수 없습니다.')
      }
    }

    document.body.appendChild(script)

    return () => {
      const scriptElement = document.getElementById(scriptId)
      if (scriptElement) {
        scriptElement.remove()
      }
      if (mapInstance.current) {
        window.naver.maps.Event.clearInstanceListeners(mapInstance.current)
      }
    }
  }, [])

  // 📌 지도 초기화 함수
  const initMap = () => {
    if (!window.naver || !window.naver.maps || !mapElement.current) {
      console.error(
        '네이버 지도 API가 로드되지 않았거나, mapElement가 없습니다.'
      )
      return
    }

    mapInstance.current = new window.naver.maps.Map(mapElement.current, {
      center: new window.naver.maps.LatLng(latitude, longitude),
      zoom: 13,
      minZoom: 7,
      zoomControl: true,
      zoomControlOptions: {
        position: window.naver.maps.Position.TOP_RIGHT,
      },
    })

    // 지도 클릭 이벤트 추가
    window.naver.maps.Event.addListener(mapInstance.current, 'click', (e) => {
      const lat = e.coord.lat()
      const lng = e.coord.lng()
      setLatLng({ latitude: lat, longitude: lng })

      // 역 지오코딩 요청
      getAddressFromCoords(lat, lng)

      // 마커 위치 변경
      if (markerInstance.current) {
        markerInstance.current.setPosition(
          new window.naver.maps.LatLng(lat, lng)
        )
      }

      // 원 위치 변경
      if (circleInstance.current) {
        circleInstance.current.setCenter(new window.naver.maps.LatLng(lat, lng))
      }
    })

    // 마커 추가
    markerInstance.current = new window.naver.maps.Marker({
      position: new window.naver.maps.LatLng(latitude, longitude),
      map: mapInstance.current,
    })

    // 원 추가
    circleInstance.current = new window.naver.maps.Circle({
      map: mapInstance.current,
      center: new window.naver.maps.LatLng(latitude, longitude),
      radius: 100,
      fillColor: 'red',
      fillOpacity: 0.4,
    })
  }

  const onChangeInstance = () => {
    setLatLng((prev) => {
      const newLat = prev.latitude + 0.001
      const newLng = prev.longitude + 0.001

      if (
        markerInstance.current &&
        mapInstance.current &&
        circleInstance.current
      ) {
        const newPosition = new window.naver.maps.LatLng(newLat, newLng)
        markerInstance.current.setPosition(newPosition)
        mapInstance.current.setCenter(newPosition)
        circleInstance.current.setCenter(newPosition)
      }

      return { latitude: newLat, longitude: newLng }
    })
  }

  return (
    <>
      <button onClick={onChangeInstance}>클릭 시 위치 변경</button>
      <p>
        <strong>클릭한 위치의 주소:</strong>{' '}
        {address || '지도에서 위치를 클릭하세요!'}
      </p>
      <div ref={mapElement} style={{ width: '100vh', height: '80vh' }} />
    </>
  )
}

export default NaverMaps
