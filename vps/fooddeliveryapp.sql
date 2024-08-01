-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-07-2024 a las 23:24:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fooddeliveryapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cart`
--

CREATE TABLE `cart` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `item_id` varchar(255) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2019_08_19_000000_create_failed_jobs_table', 1),
(4, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(5, '2023_01_23_052325_products', 1),
(6, '2023_01_23_052650_cart', 1),
(7, '2023_01_23_052852_orders', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `delivery_boy_email` varchar(255) DEFAULT NULL,
  `customer_email` varchar(255) NOT NULL,
  `items` varchar(255) NOT NULL,
  `destination_address` varchar(255) NOT NULL,
  `destination_lat` varchar(255) NOT NULL,
  `destination_lon` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `orders`
--

INSERT INTO `orders` (`id`, `delivery_boy_email`, `customer_email`, `items`, `destination_address`, `destination_lat`, `destination_lon`, `status`, `created_at`, `updated_at`) VALUES
(1, 'one@delivery.com', 'one@customer.com', '[{\"item_id\":\"1\"}]', 'San Vicente Centenario, 97325655', '14.891192561676846', '-88.28435768235195', 'success', '2024-07-29 08:18:49', '2024-07-29 08:41:36'),
(2, 'Luis@gmai.com', 'one@customer.com', '[{\"item_id\":\"2\"}]', 'Macholoa, 98051540', '14.893684894977696', '-88.28633795867721', 'success', '2024-07-29 08:37:18', '2024-07-29 08:45:18'),
(3, 'em9845368@gmail.com', 'Jose@gmail.com', '[{\"item_id\":\"3\"},{\"item_id\":\"4\"}]', 'SVC, 8771819', '14.892042518969419', '-88.28269056084355', 'failed', '2024-07-29 08:38:39', '2024-07-29 08:43:38'),
(4, 'one@delivery.com', 'sindy@gmail.com', '[{\"item_id\":\"1\"}]', 'San Nicolás, 92737282', '14.89070488422783', '-88.28563614805869', 'failed', '2024-07-29 09:41:19', '2024-07-29 09:47:42'),
(5, 'em9845368@gmail.com', 'Luis@gmai.com', '[{\"item_id\":\"1\"},{\"item_id\":\"2\"},{\"item_id\":\"3\"}]', 'El salitre, 7273662', '14.902288098852893', '-88.28994600683781', 'success', '2024-07-29 09:42:56', '2024-07-29 09:47:04'),
(6, 'em9845368@gmail.com', 'Jose@gmail.com', '[{\"item_id\":\"1\"},{\"item_id\":\"1\"},{\"item_id\":\"2\"}]', 'Sn,6177272', '14.887648680063066', '-88.28488615897344', 'success', '2024-07-29 09:57:17', '2024-07-30 05:56:14'),
(7, 'em9845368@gmail.com', 'Fernando@gmail.com', '[{\"item_id\":\"1\"},{\"item_id\":\"2\"},{\"item_id\":\"3\"},{\"item_id\":\"4\"}]', 'San Vicente Centenario, 97325655', '14.891482720655972', '-88.28435523042079', 'success', '2024-07-30 05:53:59', '2024-07-30 05:57:06'),
(8, 'luis@gmail.com', 'em9845368@gmail.com', '[{\"item_id\":\"1\"},{\"item_id\":\"2\"}]', 'Parque central, 81827', '14.888523003471064', '-88.28440487460375', 'success', '2024-07-30 05:58:24', '2024-07-30 05:59:22'),
(9, 'em9845368@gmail.com', 'em9845368@gmail.com', '[{\"item_id\":\"4\"}]', 'ED', '14.89045562974681', '-88.28615004923856', 'success', '2024-07-30 06:03:16', '2024-07-30 06:04:41'),
(10, 'em9845368@gmail.com', 'sindy@gmail.com', '[{\"item_id\":\"4\"}]', 'Nuevo celilac', '14.896862036526343', '-88.28670976123493', 'assigned', '2024-07-30 06:04:08', '2024-07-30 06:04:20'),
(11, NULL, 'sindy@gmail.com', '[{\"item_id\":\"1\"}]', 'hshs', '14.888117900302774', '-88.28575118167886', 'open', '2024-07-30 06:14:35', '2024-07-30 06:14:35'),
(12, 'luis@gmail.com', 'sindy@gmail.com', '[{\"item_id\":\"1\"},{\"item_id\":\"2\"},{\"item_id\":\"3\"}]', 'fv', '14.888150806302534', '-88.28101736243838', 'success', '2024-07-30 09:46:54', '2024-07-31 22:28:30'),
(13, NULL, 'em9845368@gmail.com', '[{\"item_id\":\"1\"},{\"item_id\":\"2\"}]', 'Macholoa', '14.895324391477288', '-88.28338991949414', 'open', '2024-07-31 22:10:07', '2024-07-31 22:10:07');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`id`, `name`, `description`, `price`, `image`, `created_at`, `updated_at`) VALUES
(1, 'Parrilla Familiar', 'la creación de un nuevo plato para el menú del restaurante Parrilla Familiar. El plato se llama  \"Parrillada Familiar\" y es una selección de las carnes más jugosas y sabrosas del restaurante,  asadas a la perfección sobre las brasas. La parrillada incluye costillas de cerdo, chorizo argentino,  picaña, pollo y una variedad de acompañamientos.', '450', 'https://images.getduna.com/4b7d38ba-f349-480d-955a-6fe3fcfb063d/6ad5b46f85c59541_domicilio_55943_1080x1080_1685986752_1701101882.png?d=400x400&format=webp', '2024-07-29 08:14:22', '2024-07-29 08:14:22'),
(2, 'Plato de Res', 'Un festín de carne para el amante de res. Disfruta de un corte de carne de res de primera calidad,  cocinado a la perfección a tu gusto y acompañado de guarniciones deliciosas, en un ambiente acogedor y relajado.', '135', 'https://www.hondurastips.hn/wp-content/uploads/2020/12/cena-3.png', '2024-07-29 08:14:52', '2024-07-29 08:14:52'),
(3, 'Plato de pollo a la plancha', 'Disfruta de un plato de pollo fresco y sabroso, cocinado a la plancha a la perfección y  acompañado de guarniciones deliciosas, en un ambiente acogedor y familiar.', '120', 'https://image.freepik.com/foto-gratis/filete-pechuga-pollo-plancha-verdura_1339-43660.jpg', '2024-07-29 08:15:20', '2024-07-29 08:15:20'),
(4, 'Plato de Chuleta', 'Disfruta de una jugosa chuleta de cerdo a la parrilla, cocinada a la perfección y acompañada de  guarniciones irresistibles, en un ambiente cálido y acogedor.', '110', 'https://buenprovecho.hn/wp-content/uploads/2020/08/Chuleta_con_tajadas_sula_-_principal.jpg', '2024-07-29 08:15:50', '2024-07-29 08:15:50');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `destination_address` varchar(255) DEFAULT NULL,
  `destination_lat` varchar(255) DEFAULT NULL,
  `destination_lon` varchar(255) DEFAULT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `type`, `name`, `email`, `email_verified_at`, `password`, `destination_address`, `destination_lat`, `destination_lon`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'admin', 'Admin', 'admin@gmail.com', NULL, '$2y$10$ghoCXfCD6Zk5MF1BLsgtauOz2/o9XBfKKdoyTG72yVIBZ.0UzBh0u', NULL, NULL, NULL, NULL, '2024-07-29 08:08:03', '2024-07-29 08:08:03'),
(3, 'customer', 'Alejandro', 'one@customer.com', NULL, '$2y$10$9BxnBH4ongorHsISoB8KSup68flo4K60tna7OQ/Cyg/na41icBKu.', 'Macholoa, 98051540', '14.893684894977696', '-88.28633795867721', NULL, '2024-07-29 08:17:29', '2024-07-29 08:37:13'),
(6, 'customer', 'Jose', 'Jose@gmail.com', NULL, '$2y$10$O/OiCUZ3enKiDeP9vdRufOCVcZoW1G0LZCYovHH1KIiM5WIUIreg6', 'Sn,6177272', '14.887648680063066', '-88.28488615897344', NULL, '2024-07-29 08:35:27', '2024-07-29 09:57:13'),
(11, 'customer', 'Sindy Zepeda', 'sindy@gmail.com', NULL, '$2y$10$LS6uIkR.j6T4rcZW/fMPOOtNVDOCX7X8c1LLL5P8LTSFb8hUwvd16', 'fv', '14.888150806302534', '-88.28101736243838', NULL, '2024-07-29 23:11:43', '2024-07-30 06:14:51'),
(13, 'delivery', 'Edg', 'em9845368@gmail.com', NULL, '$2y$10$/IRSQRe2/s7XuOcul5g9TO2vNCYNCsR8gS9mS/uu.mKPSDXD3.3Ja', 'Macholoa', '14.895324391477288', '-88.28338991949414', NULL, '2024-07-29 23:46:00', '2024-07-30 09:52:48'),
(14, 'delivery', 'Luis', 'luis@gmail.com', NULL, '$2y$10$uBDQwIWhHJ.qB5A0tiovUeaD4pQU.hRlFVaH6xsFOk4Wf0Mj.kJu2', NULL, NULL, NULL, NULL, '2024-07-29 23:46:56', '2024-07-29 23:46:56'),
(17, 'customer', 'Fernando', 'Fernando@gmail.com', NULL, '$2y$10$SQ7QmHAXFqePVO6CTmgLv.j4TerGTseySBBQmw5OkyIy0LDEhwgUW', 'San Vicente Centenario, 97325655', '14.891482720655972', '-88.28435523042079', NULL, '2024-07-30 00:02:18', '2024-07-30 01:14:59');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indices de la tabla `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `password_resets`
--
ALTER TABLE `password_resets`
  ADD PRIMARY KEY (`email`);

--
-- Indices de la tabla `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cart`
--
ALTER TABLE `cart`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
