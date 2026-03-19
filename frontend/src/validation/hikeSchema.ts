import { z } from 'zod';

export const hikeSchema = z.object({
  nameOfTrail: z.string().nonempty('Trail name is required'),
  startLocation: z.string().nonempty('Start location is required'),
  startTime: z.string().refine((value) => {
    const date = new Date(value);
    return !Number.isNaN(date.getTime()) && date >= new Date();
  }, 'Start time must be now or in the future'),
  price: z.preprocess((val) => {
    const n = Number(val);
    return Number.isNaN(n) ? undefined : n;
  }, z.number({}).positive('Price must be greater than 0')),
  lengthOfTrail: z.preprocess((val) => {
    const n = Number(val);
    return Number.isNaN(n) ? undefined : n;
  }, z.number({}).positive('Length must be greater than 0')),
});

// z.infer - automatically creates TS types
export type HikeFormValues = z.infer<typeof hikeSchema>;
