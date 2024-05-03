export interface Service {
  name: string;
  description: string;
  links: {title: string, url: string}[];
}

export interface Page {
  slug: string;
  title: string;
}