import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/LayoutComponent';
import HikeListPage from './pages/HikeListPage';
import HikeDetailsPage from './pages/HikeDetailsPage';
import HikeFormPage from './pages/HikeFormPage';
import NotFoundPage from './pages/NotFoundPage';
import { ProtectedRoute } from './components/ProtectedRoute';
import { Login } from './pages/Login';
import { Register } from './pages/Register';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/" element={<Layout />}>
          <Route
            index
            element={
              <ProtectedRoute>
                <HikeListPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="create"
            element={
              <ProtectedRoute>
                <HikeFormPage />
              </ProtectedRoute>
            }
          />
          <Route
            path=":hikeId"
            element={
              <ProtectedRoute>
                <HikeDetailsPage />
              </ProtectedRoute>
            }
          />
          <Route
            path=":hikeId/edit"
            element={
              <ProtectedRoute>
                <HikeFormPage />
              </ProtectedRoute>
            }
          />
          <Route path="*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
