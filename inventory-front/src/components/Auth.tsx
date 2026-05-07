import { useForm } from "react-hook-form";
import { motion } from "framer-motion";
import img from "../img/image.png";
import { useNavigate } from "react-router-dom";

type LoginForm = {
  email: string;
  password: string;
};

export default function Auth() {
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<LoginForm>();

  const navigate = useNavigate();

  const onSubmit = async (data: LoginForm) => {
    console.log("Form data:", data);

    try {
      const res = await fetch("http://localhost:8090/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      });

      if (!res.ok) {
        throw new Error("Credenciales inválidas");
      }

      const result = await res.json();

      localStorage.setItem("token", result.token);

      navigate("/inventory");

    } catch (error) {
      console.error("Login error", error);
    }
  };

  return (
    <div className="min-h-screen bg-blue-600 flex items-center justify-center">
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-white w-[350px] rounded-2xl shadow-xl p-8 relative"
      >
        <div className="flex justify-center -mt-16 mb-6">
          <img src={img} alt="logo" className="w-20 h-20" />
        </div>

        <h2 className="text-2xl font-bold text-center mb-6 text-gray-700">
          Iniciar Sesión
        </h2>

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">

          <input
            type="email"
            placeholder="Correo"
            className="w-full p-3 border rounded-lg"
            {...register("email", { required: "El correo es obligatorio" })}
          />
          {errors.email && (
            <p className="text-red-500 text-sm">{errors.email.message}</p>
          )}

          <input
            type="password"
            placeholder="Contraseña"
            className="w-full p-3 border rounded-lg"
            {...register("password", { required: "La contraseña es obligatoria" })}
          />
          {errors.password && (
            <p className="text-red-500 text-sm">{errors.password.message}</p>
          )}

          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-3 rounded-lg"
          >
            Ingresar
          </button>

        </form>
      </motion.div>
    </div>
  );
}