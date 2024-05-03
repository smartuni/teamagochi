export interface Service {
  name: string;
  description: string;
  body?: string;
  links: {title: string, url: string}[];
}

export interface Page {
  slug: string;
  title: string;
}