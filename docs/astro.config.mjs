import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({
	base: '/teamagochi',
	site: 'https://smartuni.github.io',
	integrations: [
		starlight({
			title: 'Teamagochi',
			sidebar: [
				{
					label: 'Basics',
					autogenerate: { directory: 'basics' },
				},
				{
					label: 'Frontend',
					autogenerate: { directory: 'frontend' },
				},
				{
					label: 'Web Backend',
					autogenerate: { directory: 'web_backend' },
				},
				{
					label: 'Node',
					autogenerate: { directory: 'node' },
				}
			],
		}),
	],
});
