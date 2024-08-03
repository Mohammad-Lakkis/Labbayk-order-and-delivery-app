using Labbayk.Data;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class PricesRepository : IPricesRepository
    {
        private readonly DataContext _context;

        public PricesRepository(DataContext context)
        {
            _context = context;
        }

        public ICollection<Price> GetPrices()
        {
            return _context.PriceRanges.OrderBy(p => p.Id).ToList();
        }
    }
}
