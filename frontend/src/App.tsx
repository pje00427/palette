import { useEffect, useState } from 'react'

interface Drop {
  id: number
  productName: string
  scheduledAt: string
  status: string
  totalQuantity: number
}

function App() {
  const [drops, setDrops] = useState<Drop[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch('/api/drops')
    .then(res => res.json())
    .then(data => {
      setDrops(data.data)
      setLoading(false)
    })
  }, [])

  if (loading) return <p>로딩 중...</p>

  return (
      <div>
        <h1>드롭 목록</h1>
        <table border={1}>
          <thead>
          <tr>
            <th>ID</th>
            <th>상품명</th>
            <th>오픈 시간</th>
            <th>상태</th>
            <th>수량</th>
          </tr>
          </thead>
          <tbody>
          {drops.map(drop => (
              <tr key={drop.id}>
                <td>{drop.id}</td>
                <td>{drop.productName}</td>
                <td>{drop.scheduledAt}</td>
                <td>{drop.status}</td>
                <td>{drop.totalQuantity}</td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  )
}

export default App