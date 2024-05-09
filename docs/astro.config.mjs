import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({
	base: '/teamagochi/docs',
	trailingSlash: 'always',
	site: 'https://smartuni.github.io',
	integrations: [
		// https://starlight.astro.build/reference/configuration/
		starlight({
			title: 'Teamagochi',
			sidebar: [
				{ label: 'Get started', link: '/get-started' },
				{
					label: 'Frontend',
					autogenerate: { directory: 'frontend' },
				},
				{
					label: 'Web Backend',
					autogenerate: { directory: 'web-backend' },
				},
				{
					label: 'Node',
					autogenerate: { directory: 'node' },
				},
				{
					label: 'Cross-Cutting',
					autogenerate: { directory: 'cross-cutting' },
				},
				{
					label: 'Guides',
					autogenerate: { directory: 'guides' },
				},
			],
			pagination: false,
			editLink: { baseUrl: 'https://github.com/smartuni/teamagochi/tree/main/docs/' },
			components: { Hero: './src/components/Hero.astro' },
			customCss: [ './src/assets/css/global.css' ],
		}),
	],
});
