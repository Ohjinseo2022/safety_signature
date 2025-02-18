import { blobFileDownload } from '@/utils/fileUtils'
import useFetchApi from '../useFetchApi'

export const getMinIoFileDownload = async (attachDocId: string) => {
  const { data, filename, error, status } = await useFetchApi(
    `/attach/download/${attachDocId}?${Math.random() * 100000}`,
    {
      method: 'get',
    }
  )
  if (status === 200) {
    let deCodefileName = decodeURIComponent(filename)
    blobFileDownload(data as Blob, deCodefileName)
  }
}
