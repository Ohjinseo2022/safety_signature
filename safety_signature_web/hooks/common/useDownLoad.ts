import { blobFileDownload } from '@/utils/fileUtils'
import useFetchApi from '../useFetchApi'

export const getMinIoFileDownload = async (attachDocId: string) => {
  const { data, filename, error, status } = await useFetchApi(
    `/attach/download/${attachDocId}?${Math.random() * 100000}`,
    {
      method: 'get',
    },
    {
      responseType: 'blob',
    }
  )
  if (status === 200) {
    //console.log(data)
    let deCodefileName = decodeURIComponent(filename)
    blobFileDownload(data as Blob, deCodefileName)
  }
}

export const getDownloadExcel = async (bulletinBoardId: string) => {
  const { data, filename, error, status } = await useFetchApi(
    `/approve/export-excel/${bulletinBoardId}`,
    { method: 'get' },
    { responseType: 'blob' }
  )
  if (status === 200) {
    let deCodefileName = decodeURIComponent(filename)
    blobFileDownload(data as Blob, deCodefileName)
  }
}
