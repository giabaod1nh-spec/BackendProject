import React, { useState } from 'react';

function App() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Login attempted:', formData);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      {/* Top Banner */}
      <div className="bg-red-500 text-white text-center py-2 font-medium">
        CHẤM ĐIỂM RÈN LUYỆN SINH VIÊN PTTHCM
      </div>

      <div className="container mx-auto px-4 pt-8 flex-grow">
        {/* Logo and Title */}
        <div className="max-w-[600px] mx-auto mb-12">
          <div className="flex center">
            <img 
              src="https://ptithcm.edu.vn/wp-content/uploads/2021/11/logoptithcm.png"
              alt="PTTHCM Logo"
              className=" h-16 object-contain"
            />
            <div className="text-red-500 leading-tight">
            </div>
          </div>
        </div>

        {/* Login Form */}
        <div className="max-w-[400px] mx-auto bg-white rounded-lg shadow-lg p-8">
          <h2 className="text-2xl font-bold text-red-500 text-center mb-6">Đăng nhập</h2>
          
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-red-500 mb-2">Tài khoản</label>
              <input
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-red-500"
                placeholder="Nhập tài khoản"
              />
            </div>

            <div>
              <label className="block text-red-500 mb-2">Mật khẩu</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-red-500"
                placeholder="Nhập mật khẩu"
              />
            </div>

            <div className="text-right">
              <a href="#" className="text-blue-500 hover:underline text-sm">
                Quên mật khẩu?
              </a>
            </div>

            <button
              type="submit"
              className="w-full bg-red-500 text-white py-2 rounded hover:bg-red-600 transition duration-200"
            >
              Đăng nhập
            </button>
          </form>
        </div>
      </div>

      {/* Footer */}
      <footer className="bg-red-500 text-white text-center py-2 mt-8">
        Copyright © 2025 Học viện Công nghệ Bưu chính Viễn thông
      </footer>
    </div>
  );
}

export default App;