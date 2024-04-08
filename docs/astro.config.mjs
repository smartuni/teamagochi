import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({
	integrations: [
		starlight({
			title: 'UNNAMED RIOT PROJECT',
			sidebar: [
				{
					label: 'Basics',
					autogenerate: { directory: 'basics' },
				}
			],
		}),
	],
});
