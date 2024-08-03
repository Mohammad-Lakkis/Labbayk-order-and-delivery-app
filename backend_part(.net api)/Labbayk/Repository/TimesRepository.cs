using Labbayk.Data;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class TimesRepository : ITimesRepository
    {
        private readonly DataContext _context;

        public TimesRepository(DataContext context)
        {
            _context = context;
        }

        public ICollection<Time> GetTimes()
        {
            return _context.TimeRanges.OrderBy(t => t.Id).ToList();
        }
    }
}
